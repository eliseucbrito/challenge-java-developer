package br.com.neurotech.challenge.factories;

import br.com.neurotech.challenge.entity.NeurotechClient;

public class TestClientFactory {
  public static NeurotechClient createDefaultClient() {
    return new NeurotechClient(1L, "John Doe", 25, 5000.0);
  }

  public static NeurotechClient createInvalidClient() {
    return new NeurotechClient(null, "", 10, -5000.0);
  }

}
