package sptech.bentscadastro.avaliation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.bentscadastro.avaliation.entity.Avaliation;

public interface AvaliationRepository extends JpaRepository<Avaliation, Integer> {
}
