package sptech.bentscadastro.image.controller;

import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.bentscadastro.data.estructure.Queue;
import sptech.bentscadastro.image.DTO.ImageDTO;
import sptech.bentscadastro.image.entity.ImageRestaurant;
import sptech.bentscadastro.image.form.UpdateImageRestaurantForm;
import sptech.bentscadastro.image.repository.ImageRestaurantRepository;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.form.ImgUrl;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping("/images")
public class ImageRestaurantController {

    @Autowired
    private ImageRestaurantRepository imageRestaurantRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    Queue<ImageRestaurant> queueImg = new Queue<>(10);

    @PostMapping(value = "/addImageInQueue/{idRestaurant}", consumes = "multipart/form-data")
    public ResponseEntity registerImageInQueue(@RequestParam MultipartFile img, @PathVariable Integer idRestaurant) throws IOException {
        if (restaurantRepository.existsById(idRestaurant)) {

            Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
            ImageRestaurant imgRestaurant = new ImageRestaurant();

            byte[] content = img.getBytes();

            UpdateImageRestaurantForm newImage = new UpdateImageRestaurantForm(content);

            imgRestaurant.setImage(content);
            imgRestaurant.setRestaurant(restaurant);

            queueImg.insert(imgRestaurant);

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/executeImageQueue")
    public ResponseEntity executeImageQueue() {

        if (queueImg.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        while (!queueImg.isEmpty()){
            imageRestaurantRepository.save(queueImg.poll());
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{idRestaurant}")
    public ResponseEntity getImageByIdRestaurant(@PathVariable Integer idRestaurant) {
        List<ImageDTO> imageRestaurants = imageRestaurantRepository.findByRestaurantIdRestaurant(idRestaurant);

        if (imageRestaurants.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        AtomicInteger index = new AtomicInteger(0);
        imageRestaurants.stream().forEach(restaurant -> {restaurant.setId(index.incrementAndGet());});

        return ResponseEntity.status(200).body(imageRestaurants);
    }
}
