package sptech.bentscadastro.restaurant.DTO;

import sptech.bentscadastro.restaurant.entity.Restaurant;

public class RestaurantDTO {

    private Integer idRestaurant;
    private String description;

    public RestaurantDTO(Integer idRestaurant, String description) {
        this.idRestaurant = idRestaurant;
        this.description = description;
    }

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
