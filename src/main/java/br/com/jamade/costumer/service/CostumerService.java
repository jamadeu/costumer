package br.com.jamade.costumer.service;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CostumerService {
    private final CostumerRepository costumerRepository;

    public Costumer findByEmail(String email) {
        return costumerRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Costumer is not found"));
    }

    public Costumer findByCpf(String cpf) {
        return costumerRepository.findByCpf(cpf)
                .orElseThrow(() -> new BadRequestException("Cpf already in use"));
    }

    public Costumer create(NewCostumerRequest newCostumerRequest) {
        if (costumerRepository.findByCpf(newCostumerRequest.getCpf()).isPresent()) {
            throw new BadRequestException("Cpf already in use");
        }
        if (costumerRepository.findByEmail(newCostumerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        return costumerRepository.save(newCostumerRequest.toCostumer());
    }
}
