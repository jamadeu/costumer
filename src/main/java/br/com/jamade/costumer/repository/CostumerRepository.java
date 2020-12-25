package br.com.jamade.costumer.repository;

import br.com.jamade.costumer.domain.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    Optional<Costumer> findByEmail(String email);

    Optional<Costumer> findByCpf(String cpf);
}
