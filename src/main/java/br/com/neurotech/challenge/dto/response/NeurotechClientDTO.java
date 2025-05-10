package br.com.neurotech.challenge.dto.response;

import br.com.neurotech.challenge.entity.NeurotechClient;

public record NeurotechClientDTO(
    String name,
    Integer age,
    Double income) {

  public static NeurotechClientDTO fromEntity(NeurotechClient client) {
    return new NeurotechClientDTO(
        client.getName(),
        client.getAge(),
        client.getIncome());
  }

  public NeurotechClient toEntity() {
    return new NeurotechClient(null, this.name, this.age, this.income);
  }
}
