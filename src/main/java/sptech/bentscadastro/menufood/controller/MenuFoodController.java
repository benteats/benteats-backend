package sptech.bentscadastro.menufood.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.bentscadastro.menufood.entity.MenuFood;
import sptech.bentscadastro.menufood.form.UpdateItemMenuFoodForm;
import sptech.bentscadastro.menufood.repository.MenuFoodRespository;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class MenuFoodController {

    @Autowired
    MenuFoodRespository menuFoodRespository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @PostMapping("/{idRestaurant}")
    public ResponseEntity<Void> registerItemInMenuFood(@RequestBody MenuFood newItem, @PathVariable Integer idRestaurant) {
        if (restaurantRepository.existsById(idRestaurant)) {
            Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
            newItem.setRestaurant(restaurant);
            menuFoodRespository.save(newItem);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{idRestaurant}")
    public ResponseEntity<List<MenuFood>> getMenuFoodByIdRestaurant(@PathVariable Integer idRestaurant) {
        List<MenuFood> itens = menuFoodRespository.findByRestaurantIdRestaurant(idRestaurant);

        if (itens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(itens);
    }

    @PatchMapping("/{idFood}")
    public ResponseEntity<Void> updateItemMenuFoodById(@RequestBody UpdateItemMenuFoodForm updateItemMenuFoodForm, @PathVariable Integer idFood) {
        if (menuFoodRespository.existsById(idFood)) {
            if (updateItemMenuFoodForm.getName() != null || updateItemMenuFoodForm.getName() != "") {
                menuFoodRespository.updateNameItemById(idFood, updateItemMenuFoodForm.getName());
            }

            if (updateItemMenuFoodForm.getPrice() != null) {
                menuFoodRespository.updatePriceItemById(idFood, updateItemMenuFoodForm.getPrice());
            }

            if (updateItemMenuFoodForm.getDescription() != null || updateItemMenuFoodForm.getDescription() != "") {
                menuFoodRespository.updateDescriptionItemById(idFood, updateItemMenuFoodForm.getDescription());
            }

            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/registerListMenuFood/{idRestaurant}")
    public ResponseEntity<Void> registerListMenuFood(@PathVariable Integer idRestaurant, @RequestBody List<MenuFood> listMenuFood) {
        if (restaurantRepository.existsById(idRestaurant)) {
            Restaurant restaurant = restaurantRepository.findByIdRestaurant(idRestaurant);
            for (MenuFood menuFood : listMenuFood) {
                menuFood.setRestaurant(restaurant);
            }

            menuFoodRespository.saveAll(listMenuFood);
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(404).build();
    }


}
