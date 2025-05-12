package br.com.neurotech.challenge.dto.request;

import java.time.LocalDate;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

public record CreateClientRequest(
    @NotBlank(message = "Name must be a valid name") String name,
    @Past(message = "The birth date must be in the past") @NotNull(message = "The birth date must not be null") LocalDate birthDate,
    @Positive(message = "Income must be a positive number") Double income) {

  public NeurotechClient toEntity() {
    return new NeurotechClient(null, this.name, this.birthDate, this.income);
  }
}