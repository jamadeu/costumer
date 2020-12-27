package br.com.jamade.costumer.controller;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import br.com.jamade.costumer.service.CostumerService;
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

@ExtendWith(SpringExtension.class)
class CostumerControllerTest {
    @InjectMocks
    private CostumerController costumerController;

    @Mock
    private CostumerService costumerServiceMock;

    @BeforeEach
    void setup() {
        BDDMockito.when(costumerServiceMock.checkEmail(ArgumentMatchers.anyString()))
                .thenReturn(new ResponseEntity<>("email@test.com is not in use", HttpStatus.OK));
        BDDMockito.when(costumerServiceMock.checkCpf(ArgumentMatchers.anyString()))
                .thenReturn(new ResponseEntity<>("265.075.120-79 not in use", HttpStatus.OK));
        BDDMockito.when(costumerServiceMock.create(ArgumentMatchers.any(NewCostumerRequest.class)))
                .thenReturn(CostumerCreator.createValidCostumer());
    }

    @Test
    @DisplayName("checkEmail returns returns status code OK when email is not in use")
    void findByEmail_ReturnsStatusCodeOk_WhenEmailIsNotInUse() {
        ResponseEntity<?> response = costumerController.checkEmail("email@test.com");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isEqualTo("email@test.com not in use");
    }

    @Test
    @DisplayName("checkEmail throws BadRequest when email is null")
    void findByEmail_ThrowsBadRequest_WhenEmailIsNull() {
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerController.checkEmail(null));
    }

    @Test
    @DisplayName("checkCpf returns status code OK when cpf is not in use")
    void checkCpf_ReturnsStatusCodeOk_WhenCpfIsNotInUse() {
        ResponseEntity<String> response = costumerController.checkCpf("265.075.120-79");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode())
                .isNotNull()
                .isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isEqualTo("265.075.120-79 not in use");
    }

    @Test
    @DisplayName("findByCpf throws BadRequest when cpf is null")
    void findByCpf_ThrowsBadRequest_WhenCostumerIsNotFound() {
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> costumerController.checkCpf(null));
    }


    @Test
    @DisplayName("create returns a new costumer when successful")
    void create_ReturnsNewCostumer_WhenSuccessful() {
        ResponseEntity<Costumer> response = costumerController.create(NewCostumerRequestCreator.createNewCostumerRequest());
        Costumer createdCostumer = response.getBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(createdCostumer)
                .isNotNull()
                .isEqualTo(CostumerCreator.createValidCostumer());
    }
}