package br.com.jamade.costumer.repository;

import br.com.jamade.costumer.domain.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerRepository extends JpaRepository<Costumer, Long> {
}
