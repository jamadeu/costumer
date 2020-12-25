package br.com.jamade.costumer.service;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostumerService {
    private final CostumerRepository costumerRepository;

    public Optional<Costumer> findByEmail(String email) {
        return costumerRepository.findByEmail(email);
    }

    public Optional<Costumer> findByCpf(String cpf) {
        return costumerRepository.findByCpf(cpf);
    }

    public Costumer create(NewCostumerRequest newCostumerRequest) {
        if (findByCpf(newCostumerRequest.getCpf()).isPresent()) {
            throw new BadRequestException("Cpf already in use");
        }
        if (findByEmail(newCostumerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        return costumerRepository.save(newCostumerRequest.toCostumer());
    }
}
