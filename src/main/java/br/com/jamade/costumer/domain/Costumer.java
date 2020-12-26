package br.com.jamade.costumer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
