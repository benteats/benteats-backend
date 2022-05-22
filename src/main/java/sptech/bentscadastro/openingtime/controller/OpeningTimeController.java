package sptech.bentscadastro.openingtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.bentscadastro.openingtime.repository.OpeningTimeRepository;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;

@RestController
@RequestMapping("/menuFoods")
public class OpeningTimeController {

    @Autowired
    private OpeningTimeRepository openingTimeRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

}
