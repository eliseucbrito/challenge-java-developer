package br.com.neurotech.challenge.exception.client;

import br.com.neurotech.challenge.exception.common.ResourceNotFoundException;

public class ClientNotFoundException extends ResourceNotFoundException {
  public ClientNotFoundException(String identifier) {
    super("Client", identifier);
  }
}