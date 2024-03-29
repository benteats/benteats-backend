package sptech.bentscadastro.restaurant.entity;

import sptech.bentscadastro.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Entity
@Table(name ="tb_restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRestaurant;
    @NotNull
    private String foodType;
    private String priceAverage;
    @NotNull
    private String openingTime;
    @NotNull
    private String closingTime;
    @NotNull
    private String description;

    private Double ratingAverage;
    
    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    private User user;

    private byte[] imgUrl;

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(String priceAverage) {
        this.priceAverage = priceAverage;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(byte[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getRatingAverage() { return ratingAverage; }

    public void setRatingAverage(Double ratingAverage) { this.ratingAverage = ratingAverage; }
}
