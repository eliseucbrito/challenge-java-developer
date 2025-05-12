package br.com.neurotech.challenge.exception.credit_modality;

import br.com.neurotech.challenge.exception.common.ResourceNotFoundException;

public class CreditModalityNotFoundException extends ResourceNotFoundException {
  public CreditModalityNotFoundException(String identifier) {
    super("Credit Modality", identifier);
  }
}
