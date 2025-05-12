package br.com.neurotech.challenge.factories;

import java.time.LocalDate;

import br.com.neurotech.challenge.entity.NeurotechClient;

public class TestClientFactory {
  public static NeurotechClient valid() {
    return new NeurotechClient(1L, "John Doe", LocalDate.now().minusYears(25), 5000.0);
  }

  public static NeurotechClient invalid() {
    return new NeurotechClient(null, "", LocalDate.now().plusYears(10), -5000.0);
  }

}
