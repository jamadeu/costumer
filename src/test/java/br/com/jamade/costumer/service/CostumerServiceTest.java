package br.com.jamade.costumer.service;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.util.CostumerCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class CostumerServiceTest {
    @InjectMocks
    private CostumerService costumerService;

    @Mock
    private CostumerRepository costumerRepositoryMock;

    @BeforeEach
    void setup() {
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CostumerCreator.createValidCostumer()));
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CostumerCreator.createValidCostumer()));
    }

    @Test
    @DisplayName("findByEmail returns an optional of costumer when successful")
    void findByEmail_ReturnsAnOptionalOfCostumer_WhenSuccessful() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        Optional<Costumer> optionalCostumer = costumerService.findByEmail(costumer.getEmail());

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isNotEmpty()
                .contains(costumer);
    }

    @Test
    @DisplayName("findByEmail returns an empty optional when costumer is not found")
    void findByEmail_ReturnsAnEmptyOptional_WhenCostumerIsNotFound() {
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        Optional<Costumer> optionalCostumer = costumerService.findByEmail("costumer not found");

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByCpf returns an optional of costumer when successful")
    void findByCpf_ReturnsAnOptionalOfCostumer_WhenSuccessful() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        Optional<Costumer> optionalCostumer = costumerService.findByCpf(costumer.getCpf());

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isNotEmpty()
                .contains(costumer);
    }

    @Test
    @DisplayName("findByCpf returns an empty optional when costumer is not found")
    void findByCpf_ReturnsAnEmptyOptional_WhenCostumerIsNotFound() {
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        Optional<Costumer> optionalCostumer = costumerService.findByCpf("costumer not found");

        Assertions.assertThat(optionalCostumer)
                .isNotNull()
                .isEmpty();
    }
}