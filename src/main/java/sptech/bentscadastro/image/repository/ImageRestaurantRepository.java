package sptech.bentscadastro.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.bentscadastro.image.DTO.ImageDTO;
import sptech.bentscadastro.image.entity.ImageRestaurant;

import java.util.List;

public interface ImageRestaurantRepository extends JpaRepository<ImageRestaurant, Integer> {

    @Query("select new sptech.bentscadastro.image.DTO.ImageDTO(i.image) from ImageRestaurant i where i.restaurant.idRestaurant = ?1")
    List<ImageDTO> findByRestaurantIdRestaurant(Integer idRestaurant);
}
