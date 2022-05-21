package sptech.bentscadastro.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.bentscadastro.restaurant.DTO.RestaurantDetailDTO;
import sptech.bentscadastro.restaurant.entity.Restaurant;

import javax.transaction.Transactional;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Modifying
    @Transactional
    @Query("update Restaurant r set r.foodType = ?2 where r.idRestaurant = ?1")
    void updateFoodTypeById(Integer idRestaurant, String value);

    @Modifying
    @Transactional
    @Query("update Restaurant r set r.priceAverage = ?2 where r.idRestaurant = ?1")
    void updatePriceAverageById(Integer idRestaurant, Double value);

    @Modifying
    @Transactional
    @Query("update Restaurant r set r.openingTime = ?2 where r.idRestaurant = ?1")
    void updateOpeningTimeById(Integer idRestaurant, String value);

    @Modifying
    @Transactional
    @Query("update Restaurant r set r.closingTime = ?2 where r.idRestaurant = ?1")
    void updateClosinTimeById(Integer idRestaurant, String value);

    @Modifying
    @Transactional
    @Query("update Restaurant r set r.description = ?2 where r.idRestaurant = ?1")
    void updateDescriptionById(Integer idRestaurant, String value);

//    boolean existsByEmailAndPassword(String email, String password);

//    boolean existsByPhoneAndPassword(String email, String password);

    @Transactional
    @Query("select r.idRestaurant from Restaurant r where r.user.email = ?1 or r.user.phone = ?1")
    Integer getIdUser(String login);

    @Transactional
    @Modifying
    @Query("update Restaurant r set r.user.isLogged = true where r.user.email = ?1 or r.user.phone = ?1 and r.user.password = ?2")
    void loginUser(String login, String password);

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:lat)) * cos(radians(r.user.lat)) *" +
            " cos(radians(r.user.lng) - radians(:lng)) + sin(radians(:lat)) * sin(radians(r.user.lat))))";
    @Query("SELECT r.user.name as name," +
            "r.user.address as address," +
            "r.user.addressNumber as addressNumber," +
            "r.foodType as foodType," +
            "r.priceAverage as priceAverage," +
            "r.openingTime as openingTime," +
            "r.description as description," +
            "r.user.lat as lat," +
            "r.user.lng as lng," +
            "r.imgUrl as imgUrl FROM Restaurant r WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY "+ HAVERSINE_FORMULA + " DESC")
    List<RestaurantDetailDTO> findRestaurantsWithInDistance(@Param("lat") double lat, @Param("lng") double lng, @Param("distance") double distanceWithInKM);
}
