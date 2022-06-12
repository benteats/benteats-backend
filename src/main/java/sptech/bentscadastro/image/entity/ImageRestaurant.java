package sptech.bentscadastro.image.entity;

import sptech.bentscadastro.restaurant.entity.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_restaurant_image")
public class ImageRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImage;


    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "idRestaurant", referencedColumnName = "idRestaurant", nullable = false)
    private Restaurant restaurant;


    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
