package sptech.bentscadastro.avaliation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.avaliation.entity.Avaliation;
import sptech.bentscadastro.avaliation.form.UpdateAvaliationForm;
import sptech.bentscadastro.avaliation.repository.AvaliationRepository;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/avaliations")
public class AvaliationController {

    @Autowired
    AvaliationRepository avaliationRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;


    @PostMapping("/{idUser}/{idRestaurant}")
    public ResponseEntity<Void> registerAvaliation(@RequestBody Avaliation newAvaliation, @PathVariable Integer idUser, @PathVariable Integer idRestaurant) {
        if (userRepository.existsById(idUser) && restaurantRepository.existsById(idRestaurant)) {
            User user = userRepository.findByIdUser(idUser);
            Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
            newAvaliation.setUser(user);
            newAvaliation.setRestaurant(restaurant);
            avaliationRepository.save(newAvaliation);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping
    public ResponseEntity<List<Avaliation>> getAllAvaliations() {
        List<Avaliation> avaliations = avaliationRepository.findAll();

        if (avaliations.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(avaliations);
    }

    @GetMapping("/getAvaliationsByIdRestaurant/{idRestaurant}")
    public ResponseEntity<List<Avaliation>> getAvaliationsByIdRestaurant(@PathVariable Integer idRestaurant) {
        List<Avaliation> avaliations = avaliationRepository.findByRestaurantIdRestaurant(idRestaurant);

        if (avaliations.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(avaliations);
    }

    @PatchMapping("/updateAvaliationById/{idAvaliation}")
    public ResponseEntity<Void> updateAvaliationById(@PathVariable Integer idAvaliation, @RequestBody UpdateAvaliationForm updateAvaliationForm) {
        if (avaliationRepository.existsById(idAvaliation)) {
            if (updateAvaliationForm.getDhAvaliation() != null) {
                avaliationRepository.updateDhAvaliationById(idAvaliation, updateAvaliationForm.getDhAvaliation());
            }

            if (updateAvaliationForm.getRating() != null) {
                avaliationRepository.updateRatingById(idAvaliation, updateAvaliationForm.getRating());
            }

            if (updateAvaliationForm.getComment() != null || updateAvaliationForm.getComment() != "") {
                avaliationRepository.updateCommentById(idAvaliation, updateAvaliationForm.getComment());
            }

            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/deleteAvaliationById/{idAvaliation}")
    public ResponseEntity<Void> deleteAvaliationById(@PathVariable Integer idAvaliation) {
        if (avaliationRepository.existsById(idAvaliation)) {
            avaliationRepository.deleteById(idAvaliation);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }
}
