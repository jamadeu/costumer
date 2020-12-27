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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                .thenReturn(Optional.empty());
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        BDDMockito.when(costumerRepositoryMock.save(ArgumentMatchers.any(Costumer.class)))
                .thenReturn(CostumerCreator.createValidCostumer());
    }

    @Test
    @DisplayName("checkEmail returns status code OK when email is not in use")
    void checkEmail_ReturnsStatusCodeOk_WhenEmailIsNotInUse() {
        ResponseEntity<String> response = costumerService.checkEmail("email@test.com");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("email@test.com is not in use");

    }

    @Test
    @DisplayName("findByEmail throws BadRequestException when email already in use")
    void findByEmail_ThrowsBadRequestException_WhenEmailIsAlreadyInUse() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(costumer));
        String email = costumer.getEmail();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.checkEmail(email));
    }

    @Test
    @DisplayName("checkCpf returns status code OK when cpf is not in use")
    void checkCpf_ReturnsStatusCodeOk_WhenCpfIsNotInUse() {
        ResponseEntity<String> response = costumerService.checkCpf("265.075.120-79");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("265.075.120-79 is not in use");
    }

    @Test
    @DisplayName("checkCpf throws BadRequestException when cpf already in use")
    void checkCpf_ThrowsBadRequestException_WhenCpfIsAlreadyInUse() {
        Costumer costumer = CostumerCreator.createValidCostumer();
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(costumer));
        String cpf = costumer.getCpf();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.checkCpf(cpf));
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
        BDDMockito.when(costumerRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CostumerCreator.createValidCostumer()));
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.create(newCostumerRequest));
    }

    @Test
    @DisplayName("create throws BadRequestException when cpf already in use")
    void create_ThrowsBadRequestException_WhenCpfAlreadyInUse() {
        BDDMockito.when(costumerRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(CostumerCreator.createValidCostumer()));
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerService.create(newCostumerRequest));
    }
}