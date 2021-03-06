package br.com.jamade.costumer.service;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.repository.CostumerRepository;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CostumerService {
    private final CostumerRepository costumerRepository;

    public ResponseEntity<String> checkEmail(String email) {
        if (costumerRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException(email + " is already in use");
        }
        return new ResponseEntity<>(email + " is not in use", HttpStatus.OK);
    }

    public ResponseEntity<String> checkCpf(String cpf) {
        if (costumerRepository.findByCpf(cpf).isPresent()) {
            throw new BadRequestException(cpf + " is already in use");
        }
        return new ResponseEntity<>(cpf + " is not in use", HttpStatus.OK);
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
