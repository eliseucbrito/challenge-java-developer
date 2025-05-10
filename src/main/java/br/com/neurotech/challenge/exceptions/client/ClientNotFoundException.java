package br.com.neurotech.challenge.exceptions.client;

import br.com.neurotech.challenge.exceptions.common.ResourceNotFoundException;

public class ClientNotFoundException extends ResourceNotFoundException {
  public ClientNotFoundException(String identifier) {
    super("Client", identifier);
  }
}