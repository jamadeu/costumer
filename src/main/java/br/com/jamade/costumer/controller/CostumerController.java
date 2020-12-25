package br.com.jamade.costumer.controller;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.service.CostumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("costumers")
@RequiredArgsConstructor
public class CostumerController {
    private final CostumerService costumerService;

    @GetMapping("/find-by-email/{email}")
    @Operation(summary = "Find costumer by email",
            tags = {"costumers"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When costumer does not found")
    })
    public ResponseEntity<Costumer> findByEmail(@PathVariable String email) {
        if (email == null) {
            throw new BadRequestException("Email can not be null");
        }
        Optional<Costumer> costumerOptional = costumerService.findByEmail(email);
        if (costumerOptional.isEmpty()) {
            throw new BadRequestException("Costumer not found");
        }
        return new ResponseEntity<>(costumerOptional.get(), HttpStatus.OK);
    }
}
