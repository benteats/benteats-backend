package sptech.bentscadastro.menufood.entity;

import sptech.bentscadastro.restaurant.entity.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_menu_food")
public class MenuFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFood;

    @Size(min = 3, max = 50)
    private String name;

    private Double price;

    @Size(min = 3, max = 200)
    private String description;

    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "idRestaurant", referencedColumnName = "idRestaurant", nullable = false)
    private Restaurant restaurant;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getIdFood() {
        return idFood;
    }

    public void setIdFood(Integer idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
