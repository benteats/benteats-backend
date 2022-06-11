package sptech.bentscadastro.image.controller;

import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.bentscadastro.data.estructure.Queue;
import sptech.bentscadastro.image.entity.ImageRestaurant;
import sptech.bentscadastro.image.form.UpdateImageRestaurantForm;
import sptech.bentscadastro.image.repository.ImageRestaurantRepository;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.form.ImgUrl;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageRestaurantController {

    @Autowired
    private ImageRestaurantRepository imageRestaurantRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    Queue<ImageRestaurant> queueImg = new Queue<>(10);

    @PostMapping(value = "/addimageinqueue/{idRestaurant}", consumes = "multipart/form-data")
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

    @PostMapping("/executeimagequeue")
    public ResponseEntity executeImageQueue() {

        if (queueImg.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        while (!queueImg.isEmpty()){
            imageRestaurantRepository.save(queueImg.poll());
        }
        return ResponseEntity.status(200).build();
    }
}