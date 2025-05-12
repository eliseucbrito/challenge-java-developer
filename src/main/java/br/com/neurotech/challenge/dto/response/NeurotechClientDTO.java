package br.com.neurotech.challenge.dto.response;

import java.time.LocalDate;

import br.com.neurotech.challenge.entity.NeurotechClient;

public record NeurotechClientDTO(
    String name,
    LocalDate birthDate,
    Integer age,
    Double income) {

  public static NeurotechClientDTO fromEntity(NeurotechClient client) {
    return new NeurotechClientDTO(
        client.getName(),
        client.getBirthDate(),
        client.getAge(),
        client.getIncome());
  }
}
