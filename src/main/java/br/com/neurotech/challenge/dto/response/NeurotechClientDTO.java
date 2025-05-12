package br.com.neurotech.challenge.dto.response;

import br.com.neurotech.challenge.entity.NeurotechClient;

public record NeurotechClientDTO(
    String name,
    String birthDate,
    Integer age,
    Double income) {

  public static NeurotechClientDTO fromEntity(NeurotechClient client) {
    return new NeurotechClientDTO(
        client.getName(),
        client.getBirthDate().toString(),
        client.getAge(),
        client.getIncome());
  }
}
