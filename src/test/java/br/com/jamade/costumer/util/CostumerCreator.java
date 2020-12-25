package br.com.jamade.costumer.util;

import br.com.jamade.costumer.domain.Costumer;

import java.time.LocalDateTime;

public class CostumerCreator {
    public static Costumer createValidCostumer() {
        return Costumer.builder()
                .id(1L)
                .name("Costumer")
                .cpf("376.078.410-08")
                .email("costumer@test.com")
                .dateOfBirth(LocalDateTime.of(2000, 12, 12, 12, 12))
                .build();
    }
}
