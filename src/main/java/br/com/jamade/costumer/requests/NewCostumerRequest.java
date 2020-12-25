package br.com.jamade.costumer.requests;

import br.com.jamade.costumer.domain.Costumer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCostumerRequest {
    @NotEmpty(message = "Costumer name can not be null")
    @Schema(description = "This is the costumer's name")
    private String name;

    @NotEmpty(message = "Costumer email can not be null")
    @Email(message = "The costumer email must be in a valid email format")
    @Schema(description = "This is the costumer's email")
    private String email;

    @CPF(message = "CPF must be in a valid email format")
    @NotEmpty(message = "Costumer CPF can not be null")
    @Schema(description = "This is the costumer's cpf")
    private String cpf;

    @NotNull(message = "Date Of Birth can not be null")
    @Past(message = "Date of birth must be a date passed")
    @Schema(description = "This is the costumer's date of birth")
    private LocalDateTime dateOfBirth;

    public Costumer toCostumer() {
        return Costumer.builder()
                .name(name)
                .email(email)
                .cpf(cpf)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
