package sptech.bentscadastro.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.restaurant.DTO.RestaurantDetailDTO;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.form.RestaurantUpdateForm;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{idUser}")
    public ResponseEntity registerRestaurant(@RequestBody @Valid Restaurant newRestaurant, @PathVariable Integer idUser) {
        if(userRepository.existsById(idUser)) {
            User restaurantUser = userRepository.findByIdUser(idUser);
            newRestaurant.setUser(restaurantUser);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(restaurants);
    }

    @DeleteMapping("/{idRestaurant}")
    public ResponseEntity deleteRestaurantById(@PathVariable Integer idRestaurant) {
        if (restaurantRepository.existsById(idRestaurant)) {
            restaurantRepository.deleteById(idRestaurant);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PatchMapping("/{idRestaurant}")
    private ResponseEntity updateUserById(@RequestBody RestaurantUpdateForm restaurant, @PathVariable Integer idRestaurant) {
        ResponseEntity result = ResponseEntity.status(404).build();
        if (restaurantRepository.existsById(idRestaurant)) {
            if (restaurant.getFoodType() != null && !restaurant.getFoodType().equals("")) {
                restaurantRepository.updateFoodTypeById(idRestaurant, restaurant.getFoodType());
                result = ResponseEntity.status(200).build();
            }

            if (restaurant.getPriceAverage() != null && !restaurant.getPriceAverage().equals("")) {
                restaurantRepository.updatePriceAverageById(idRestaurant, restaurant.getPriceAverage());
                result = ResponseEntity.status(200).build();
            }

            if (restaurant.getOpeningTime() != null && !restaurant.getOpeningTime().equals("")) {
                restaurantRepository.updateOpeningTimeById(idRestaurant, restaurant.getOpeningTime());
                result = ResponseEntity.status(200).build();
            }

            if (restaurant.getClosingTime() != null && !restaurant.getClosingTime().equals("")) {
                restaurantRepository.updateClosinTimeById(idRestaurant, restaurant.getClosingTime());
                result = ResponseEntity.status(200).build();
            }

            if (restaurant.getDescription() != null && !restaurant.getDescription().equals("")) {
                restaurantRepository.updateDescriptionById(idRestaurant, restaurant.getDescription());
                result = ResponseEntity.status(200).build();
            }
        }
        return result;
    }

    @GetMapping("/getRestaurantByCoordinates/{lat}/{lng}")
    private ResponseEntity getRestaurantByCoordinates(@PathVariable Float lat, @PathVariable Float lng) {
        List<RestaurantDetailDTO> restaurants = restaurantRepository.findRestaurantsWithInDistance(lat, lng, 5);
        if (restaurants.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(restaurants);
    }

}
