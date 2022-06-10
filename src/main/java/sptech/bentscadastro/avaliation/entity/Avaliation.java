package sptech.bentscadastro.avaliation.entity;

import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "tb_avaliation")
public class Avaliation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliation")
    private Integer idAvaliation;

    private Date dhAvaliation;

    private Double rating;

    @NotBlank
    private String comment;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idRestaurant", referencedColumnName = "idRestaurant", nullable = false)
    private Restaurant restaurant;

    public Integer getIdAvaliation() {
        return idAvaliation;
    }

    public void setIdAvaliation(Integer idAvaliation) {
        this.idAvaliation = idAvaliation;
    }

    public Date getDhAvaliation() {
        return dhAvaliation;
    }

    public void setDhAvaliation(Date dhAvaliation) {
        this.dhAvaliation = dhAvaliation;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
