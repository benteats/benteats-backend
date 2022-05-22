package sptech.bentscadastro.openingtime.entity;

import sptech.bentscadastro.restaurant.entity.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OpeningTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTime;

    @NotNull
    private String dayName;

    @NotNull
    private String opening;

    @NotNull
    private String closing;

    @ManyToOne
    private Restaurant restaurant;


    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime(Integer idTime) {
        this.idTime = idTime;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
