package sptech.bentscadastro.restaurant.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.restaurant.repository.RestaurantRepository;
import sptech.bentscadastro.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = {RestaurantController.class})
class RestaurantControllerTest {

    @Autowired
    RestaurantController controller;

    @MockBean
    RestaurantRepository restaurantRepository;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("Retorna o status 404 caso o id de usuário seja inválido")
    void registerRestaurant_invalidIdUser() {
        Restaurant restaurant = new Restaurant();
        Integer idUser = 1;

        when(userRepository.existsById(idUser)).thenReturn(false);

        ResponseEntity response = controller.registerRestaurant(restaurant, idUser);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Restorna o status 201 e cria um novo restaurate")
    void registerRestaurant_validIdUser() {
        Restaurant restaurant = new Restaurant();
        Integer idUser = 1;

        when(userRepository.existsById(idUser)).thenReturn(true);

        ResponseEntity response = controller.registerRestaurant(restaurant, idUser);
        assertEquals(CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Retorna o status 204 caso não exista nenhum restaurante cadastrado na base")
    void getAllRestaurants_returnEmpty() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<Restaurant>());

        ResponseEntity response = controller.getAllRestaurants();
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Retorna o status 200 e uma lista de restaurantes")
    void getAllRestaurants_returnListRestaurant() {
        Restaurant restaurant1 = new Restaurant();
        Restaurant restaurant2 = new Restaurant();
        Restaurant restaurant3 = new Restaurant();
        Restaurant restaurant4 = new Restaurant();

        when(restaurantRepository.findAll()).thenReturn(
                List.of(restaurant1, restaurant2, restaurant3, restaurant4)
        );

        ResponseEntity response = controller.getAllRestaurants();
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void deleteRestaurantById_invalidId() {
        Integer id = 1;
        when(restaurantRepository.existsById(id)).thenReturn(false);
        ResponseEntity response = controller.deleteRestaurantById(id);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteRestaurantById_validId() {
        Integer id = 1;
        when(restaurantRepository.existsById(id)).thenReturn(true);
        ResponseEntity response = controller.deleteRestaurantById(id);
        assertEquals(OK, response.getStatusCode());
    }
}