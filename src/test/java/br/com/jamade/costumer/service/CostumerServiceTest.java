package br.com.jamade.costumer.service;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import br.com.jamade.costumer.util.CostumerCreator;
import br.com.jamade.costumer.util.NewCostumerRequestCreator;
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
        BDDMockito.when(costumerRepositoryMock.save(ArgumentMatchers.any(Costumer.class)))
                .thenReturn(CostumerCreator.createValidCostumer());
    }

    @Test
    @DisplayName("findByEmail returns a costumer when successful")
    void findByEmail_ReturnsCostumer_WhenSuccessful() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        Costumer foundedCostumer = costumerService.findByEmail(costumer.getEmail());

        Assertions.assertThat(foundedCostumer)
                .isNotNull()
                .isEqualTo(costumer);
    }

    @Test
    @DisplayName("findByEmail throws BadRequestException when costumer is not found")
    void findByEmail_ThrowsBadRequestException_WhenCostumerIsNotFound() {
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.findByEmail("costumer not found"));
    }

    @Test
    @DisplayName("findByCpf returns a costumer when successful")
    void findByCpf_ReturnsCostumer_WhenSuccessful() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        Costumer foundedCostumer = costumerService.findByCpf(costumer.getCpf());

        Assertions.assertThat(foundedCostumer)
                .isNotNull()
                .isEqualTo(costumer);
    }

    @Test
    @DisplayName("findByCpf thorws BadRequestException when costumer is not found")
    void findByCpf_ReturnsAnEmptyOptional_WhenCostumerIsNotFound() {
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.findByCpf("costumer not found"));
    }

    @Test
    @DisplayName("create returns a costumer when successful")
    void create_ReturnsCostumer_WhenSuccessful() {
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        Costumer costumer = CostumerCreator.createValidCostumer();
        Costumer createdCostumer = costumerService.create(NewCostumerRequestCreator.createNewCostumerRequest());

        Assertions.assertThat(createdCostumer).isNotNull()
                .isEqualTo(costumer);
    }

    @Test
    @DisplayName("create throws BadRequestException when email already in use")
    void create_ThrowsBadRequestException_WhenEmailAlreadyInUse() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.create(newCostumerRequest));
    }

    @Test
    @DisplayName("create throws BadRequestException when cpf already in use")
    void create_ThrowsBadRequestException_WhenCpfAlreadyInUse() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.create(newCostumerRequest));
    }
}