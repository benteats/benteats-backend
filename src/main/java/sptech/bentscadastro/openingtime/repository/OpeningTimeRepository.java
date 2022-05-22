package sptech.bentscadastro.openingtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.bentscadastro.openingtime.entity.OpeningTime;

public interface OpeningTimeRepository extends JpaRepository<OpeningTime, Integer> {
}
