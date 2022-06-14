package sptech.bentscadastro.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.data.estructure.Queue;
import sptech.bentscadastro.data.estructure.Stack;
import sptech.bentscadastro.restaurant.DTO.RestaurantDTO;
import sptech.bentscadastro.restaurant.DTO.RestaurantDetailDTO;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.form.ImgUrl;
import sptech.bentscadastro.restaurant.form.RestaurantUpdateForm;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;
import sptech.bentscadastro.user.DTO.UserDetailDTO;
import sptech.bentscadastro.user.entity.User;
import sptech.bentscadastro.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/restaurants")
public class RestaurantController {

    Queue queueImg = new Queue(5);
    Stack stack = new Stack(5);

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{idUser}")
    public ResponseEntity registerRestaurant(@RequestBody @Valid Restaurant newRestaurant, @PathVariable Integer idUser) {
        if(userRepository.existsById(idUser)) {
            User restaurantUser = userRepository.findByIdUser(idUser);
            newRestaurant.setUser(restaurantUser);
            restaurantRepository.save(newRestaurant);
            return ResponseEntity.status(201).body(newRestaurant.getIdRestaurant());
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
//    @PostMapping("/registerImgUrl/{idRestaurant}")
//    public ResponseEntity registerImgUrl(@RequestBody ImgUrl imgUrl, @PathVariable Integer idRestaurant) {
//        if (restaurantRepository.existsById(idRestaurant)) {
//            Restaurant restaurant = restaurantRepository.getById(idRestaurant);
//            restaurant.setImgUrl(imgUrl.getImgUrl());
//            restaurantRepository.save(restaurant);
//            return ResponseEntity.status(200).build();
//        }
//
//        return ResponseEntity.status(404).build();
//    }

    @GetMapping("/getImgUrlByIdRestaurant/{idRestaurant}")
    public ResponseEntity<byte[]> getImgUrlByIdRestaurant(@PathVariable Integer idResturant) {
        if (restaurantRepository.existsById(idResturant)) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(idResturant);
            return ResponseEntity.status(200).body(restaurant.get().getImgUrl());
        }

        return ResponseEntity.status(404).build();
    }

//    @PostMapping("/registerImgInQueue/{idRestaurant}")
//    public ResponseEntity registerImgInQueue(@RequestBody ImgUrl imgUrl, @PathVariable Integer idRestaurant) {
//        if (restaurantRepository.existsById(idRestaurant)) {
//            queueImg.insert(imgUrl);
//            return ResponseEntity.status(200).build();
//        }
//        return ResponseEntity.status(404).build();
//    }
//
//    @PostMapping("/executeImgQueue/{idRestaurant}")
//    public ResponseEntity executeImgQueue(@RequestBody ImgUrl imgUrl, @PathVariable Integer idRestaurant) {
//        if (restaurantRepository.existsById(idRestaurant)) {
//            Restaurant restaurant = restaurantRepository.getById(idRestaurant);
//            for (int i = 0; i < queueImg.queueSize(); i++) {
//                restaurant.setImgUrl(queueImg.poll().toString());
//                restaurantRepository.save(restaurant);
//            }
//            queueImg.clearQueue();
//            imgUrl.setSize(0);
//            return ResponseEntity.status(200).build();
//        }
//        return ResponseEntity.status(404).build();
//    }

    @GetMapping("/getRestaurantById/{idRestaurant}")
    public ResponseEntity<Optional<Restaurant>> getRestaurantById(@PathVariable Integer idRestaurant) {
        if (restaurantRepository.existsById(idRestaurant)) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(idRestaurant);
            return ResponseEntity.status(200).body(restaurant);
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/historicStack/{idRestaurant}")
    public ResponseEntity historicStack(@PathVariable Integer idRestaurant) {
        List<RestaurantDTO> res;
        if (restaurantRepository.existsById(idRestaurant)) {
            res = restaurantRepository.findHistoricStack(idRestaurant);
            stack.push(res);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/getHistoricStack")
    public ResponseEntity getHistoricStack() {
        if (stack.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(stack.getPilha());
    }

    @GetMapping("/getStackSize")
    public ResponseEntity getStackSize() {
        return ResponseEntity.status(200).body(stack.getSize());
    }

    @GetMapping("/getIdRestaurantByIdUser/{idUser}")
    public ResponseEntity<Integer> getIdRestaurantByIdUser(@PathVariable Integer idUser) {
        if (userRepository.existsById(idUser)) {
            Integer idRestaurant = restaurantRepository.findIdRestaurantByIdUser(idUser);
            return ResponseEntity.status(200).body(idRestaurant);
        }

        return ResponseEntity.status(404).build();
    }

}
