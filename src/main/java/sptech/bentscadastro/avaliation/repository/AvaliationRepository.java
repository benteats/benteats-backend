package sptech.bentscadastro.avaliation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.bentscadastro.avaliation.entity.Avaliation;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface AvaliationRepository extends JpaRepository<Avaliation, Integer> {
    List<Avaliation> findByRestaurantIdRestaurant(Integer idRestaurant);

    @Transactional
    @Modifying
    @Query("update Avaliation a set a.dhAvaliation = ?2 where a.idAvaliation = ?1")
    void updateDhAvaliationById(Integer idAvaliation, Date value);

    @Transactional
    @Modifying
    @Query("update Avaliation a set a.rating = ?2 where a.idAvaliation = ?1")
    void updateRatingById(Integer idAvaliation, Double value);

    @Transactional
    @Modifying
    @Query("update Avaliation a set a.comment = ?2 where a.idAvaliation = ?1")
    void updateCommentById(Integer idAvaliation, String value);
}
