package sptech.bentscadastro.image.controller;

import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.bentscadastro.data.estructure.Queue;
import sptech.bentscadastro.data.estructure.Stack;
import sptech.bentscadastro.image.DTO.ImageDTO;
import sptech.bentscadastro.image.entity.ImageRestaurant;
import sptech.bentscadastro.image.form.UpdateImageRestaurantForm;
import sptech.bentscadastro.image.repository.ImageRestaurantRepository;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.form.ImgUrl;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;

import javax.servlet.MultipartConfigElement;
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
    Stack<ImageRestaurant> stackImg = new Stack<>(10);

    @PostMapping(value = "/addImageInStack/{idRestaurant}", consumes = "multipart/form-data")
    public ResponseEntity registerImageInQueue(@RequestParam MultipartFile img, @PathVariable Integer idRestaurant) throws IOException {
        if (restaurantRepository.existsById(idRestaurant)) {

            Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
            ImageRestaurant imgRestaurant = new ImageRestaurant();

            byte[] content = img.getBytes();

            UpdateImageRestaurantForm newImage = new UpdateImageRestaurantForm(content);

            restaurant.setImgUrl(content);
            restaurantRepository.save(restaurant);
            imgRestaurant.setImage(content);
            imgRestaurant.setRestaurant(restaurant);

            stackImg.push(imgRestaurant);

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping(value = "saveImage/{idRestaurant}", consumes = "multipart/form-data")
    public ResponseEntity registerImageByIdRestaurant(@RequestParam MultipartFile[] imgs, @PathVariable Integer idRestaurant) throws IOException {
        Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
        restaurant.setImgUrl(imgs[0].getBytes());
        restaurantRepository.save(restaurant);
        if (restaurantRepository.existsById(idRestaurant)) {
            for (MultipartFile img : imgs) {
                ImageRestaurant imageRestaurant = new ImageRestaurant();
                imageRestaurant.setImage(img.getBytes());
                imageRestaurant.setRestaurant(restaurantRepository.findByIdRestaurant(idRestaurant));
                imageRestaurantRepository.save(imageRestaurant);
            }

            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping("/executeImageStack")
    public ResponseEntity executeImageQueue() {

        if (stackImg.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        while (!stackImg.isEmpty()){
            imageRestaurantRepository.save(stackImg.pop());
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
