package br.com.jamade.costumer.util;

import br.com.jamade.costumer.requests.NewCostumerRequest;

import java.time.LocalDateTime;

public class NewCostumerRequestCreator {
    public static NewCostumerRequest createNewCostumerRequest() {
        return NewCostumerRequest.builder()
                .name("Costumer")
                .cpf("376.078.410-08")
                .email("costumer@test.com")
                .dateOfBirth(LocalDateTime.of(2000, 12, 12, 12, 12))
                .build();
    }
}
