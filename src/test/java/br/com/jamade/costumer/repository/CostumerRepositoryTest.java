package br.com.jamade.costumer.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("CostumerRepository tests")
class CostumerRepositoryTest {
    @Autowired
    private CostumerRepository costumerRepository;

    @Test
    @DisplayName("findByEmail returns a costumer when successful")
    void findByEmail_ReturnsACostumer_WhenSuccessful(){

    }
}