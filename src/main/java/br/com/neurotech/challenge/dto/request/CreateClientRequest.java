package br.com.neurotech.challenge.dto.request;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateClientRequest(
    @NotBlank(message = "Name must be a valid name") String name,
    @Min(value = 18, message = "Age must be at least 18") Integer age,
    @Positive(message = "Income must be a positive number") Double income) {

  public NeurotechClient toEntity() {
    return new NeurotechClient(null, this.name, this.age, this.income);
  }
}