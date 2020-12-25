package br.com.jamade.costumer.controller;

import br.com.jamade.costumer.domain.Costumer;
import br.com.jamade.costumer.exception.BadRequestException;
import br.com.jamade.costumer.requests.NewCostumerRequest;
import br.com.jamade.costumer.service.CostumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return new ResponseEntity<>(costumerService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/find-by-cpf/{cpf}")
    @Operation(summary = "Find costumer by cpf",
            tags = {"costumers"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When costumer does not found")
    })
    public ResponseEntity<Costumer> findByCpf(@PathVariable String cpf) {
        if (cpf == null) {
            throw new BadRequestException("Cpf can not be null");
        }
        return new ResponseEntity<>(costumerService.findByCpf(cpf), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new costumer",
            description = "The fields name, cpf, email and date of birth are mandatory, " +
                    "cpf and email must be unique" +
                    "date of birth must be a date passed",
            tags = {"costumers"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When there is an error with some mandatory field")
    })
    public ResponseEntity<Costumer> create(@RequestBody @Valid NewCostumerRequest newCostumerRequest) {
        return new ResponseEntity<>(costumerService.create(newCostumerRequest), HttpStatus.CREATED);
    }
}
