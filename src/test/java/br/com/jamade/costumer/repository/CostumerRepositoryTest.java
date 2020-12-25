package br.com.jamade.costumer.repository;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.util.CostumerCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("CostumerRepository tests")
class CostumerRepositoryTest {
    @Autowired
    private CostumerRepository costumerRepository;

    @Test
    @DisplayName("findByEmail returns an optional of costumer when successful")
    void findByEmail_ReturnsAnOptionalOfCostumer_WhenSuccessful() {
        Costumer costumer = costumerRepository.save(CostumerCreator.createValidCostumer());
        Optional<Costumer> optionalCostumer = costumerRepository.findByEmail(costumer.getEmail());

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isNotEmpty()
                .contains(costumer);
    }

    @Test
    @DisplayName("findByEmail returns an empty optional when costumer is not found")
    void findByEmail_ReturnsAnEmptyOptional_WhenCostumerIsNotFound() {
        Optional<Costumer> optionalCostumer = costumerRepository.findByEmail("costumer not found");

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isEmpty();
    }
}