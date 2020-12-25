package br.com.jamade.costumer.integration;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import br.com.jamade.costumer.util.CostumerCreator;
import br.com.jamade.costumer.util.NewCostumerRequestCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CostumerControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CostumerRepository costumerRepository;

    @Test
    @DisplayName("findByEmail returns costumer when successful")
    void findById_ReturnsCostumer_WhenSuccessful() {
        Costumer savedCostumer = costumerRepository.save(CostumerCreator.createCostumerToBeSaved());
        String url = String.format("/costumers/find-by-email/%s", savedCostumer.getEmail());
        ResponseEntity<Costumer> response = testRestTemplate.getForEntity(url, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(savedCostumer);
    }

    @Test
    @DisplayName("findByEmail returns BadRequest when costumer is not found")
    void findById_ReturnsBadRequest_WhenCostumerIsNotFound() {
        String url = String.format("/costumers/find-by-email/%s", "email-not-exists");
        ResponseEntity<BadRequestException> response = testRestTemplate.exchange(url,
                HttpMethod.GET, null, BadRequestException.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("findByCpf returns costumer when successful")
    void findByCpf_ReturnsCostumer_WhenSuccessful() {
        Costumer savedCostumer = costumerRepository.save(CostumerCreator.createCostumerToBeSaved());
        String url = String.format("/costumers/find-by-cpf/%s", savedCostumer.getCpf());
        ResponseEntity<Costumer> response = testRestTemplate.getForEntity(url, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(savedCostumer);
    }

    @Test
    @DisplayName("findByCpf returns BadRequest when costumer is not found")
    void findByCpf_ReturnsBadRequest_WhenCostumerIsNotFound() {
        String url = String.format("/costumers/find-by-cpf/%s", "cpf-not-exists");
        ResponseEntity<BadRequestException> response = testRestTemplate.exchange(url,
                HttpMethod.GET, null, BadRequestException.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns a costumer when successful")
    void create_ReturnsCostumer_WhenSuccessful() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("create returns BadRequest when name is null")
    void create_ReturnsBadRequest_WhenNameIsNull() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setName(null);
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when cpf is null")
    void create_ReturnsBadRequest_WhenCpfIsNull() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setCpf(null);
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when cpf is invalid")
    void create_ReturnsBadRequest_WhenCpfIsInvalid() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setCpf("00000000000");
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when cpf is already in use")
    void create_ReturnsBadRequest_WhenCpfIsAlreadyInUse() {
        costumerRepository.save(CostumerCreator.createValidCostumer());
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setEmail("anotheremail@test.com");
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when email is null")
    void create_ReturnsBadRequest_WhenEmailIsNull() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setEmail(null);
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when email is already in use")
    void create_ReturnsBadRequest_WhenEmailIsAlreadyInUse() {
        costumerRepository.save(CostumerCreator.createValidCostumer());
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setCpf("841.503.260-96");
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when email is invalid")
    void create_ReturnsBadRequest_WhenEmailIsInvalid() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setEmail("invalid-email");
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when date of birth is null")
    void create_ReturnsBadRequest_WhenDateOfBirthIsNull() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setDateOfBirth(null);
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("create returns BadRequest when date of birth is future")
    void create_ReturnsBadRequest_WhenDateOfBirthIsFuture() {
        NewCostumerRequest newCostumerRequest = NewCostumerRequestCreator.createNewCostumerRequest();
        newCostumerRequest.setDateOfBirth(LocalDateTime.of(2999, 12, 12, 12, 12));
        ResponseEntity<Costumer> response = testRestTemplate.postForEntity("/costumers", newCostumerRequest, Costumer.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
