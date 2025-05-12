package br.com.neurotech.challenge.exception.credit;

import br.com.neurotech.challenge.exception.common.ResourceNotFoundException;

public class AutomotiveCreditNotFoundException extends ResourceNotFoundException {
  public AutomotiveCreditNotFoundException(String identifier) {
    super("Automotive Credit", identifier);
  }
}
