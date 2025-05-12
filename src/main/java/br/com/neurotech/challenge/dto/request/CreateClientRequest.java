package br.com.neurotech.challenge.dto.request;

import java.time.LocalDate;

import br.com.neurotech.challenge.entity.NeurotechClient;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

@Schema(name = "CreateClientRequest", description = "Request payload to create a new client")
public record CreateClientRequest(

    @NotBlank(message = "Name must be a valid name") @Schema(description = "Full name of the client", example = "Jane Doe", requiredMode = RequiredMode.REQUIRED) String name,

    @NotNull(message = "The birth date must not be null") @Past(message = "The birth date must be in the past") @Schema(description = "Client’s birth date in ISO format", example = "2002-04-15", format = "date", requiredMode = RequiredMode.REQUIRED) LocalDate birthDate,

    @Positive(message = "Income must be a positive number") @Schema(description = "Monthly income of the client", example = "5500.00", requiredMode = RequiredMode.REQUIRED) Double income

) {

  public NeurotechClient toEntity() {
    return new NeurotechClient(null, this.name, this.birthDate, this.income);
  }
}