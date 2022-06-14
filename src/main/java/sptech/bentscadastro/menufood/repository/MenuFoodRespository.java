package sptech.bentscadastro.menufood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.bentscadastro.menufood.entity.MenuFood;

import javax.transaction.Transactional;
import java.util.List;

public interface MenuFoodRespository extends JpaRepository<MenuFood, Integer> {
    List<MenuFood> findByRestaurantIdRestaurant(Integer idRestaurant);

    @Transactional
    @Modifying
    @Query("update MenuFood m set m.name = ?2 where m.idFood = ?1")
    void updateNameItemById(Integer idFood, String value);

    @Transactional
    @Modifying
    @Query("update MenuFood m set m.price = ?2 where m.idFood = ?1")
    void updatePriceItemById(Integer idFood, Double value);

    @Transactional
    @Modifying
    @Query("update MenuFood m set m.description = ?2 where m.idFood = ?1")
    void updateDescriptionItemById(Integer idFood, String value);
}
