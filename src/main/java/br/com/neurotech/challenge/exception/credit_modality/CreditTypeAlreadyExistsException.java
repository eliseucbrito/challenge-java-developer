package br.com.neurotech.challenge.exception.credit_modality;

import org.springframework.http.HttpStatus;

import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import br.com.neurotech.challenge.exception.ApiException;

public class CreditTypeAlreadyExistsException extends ApiException {
  public CreditTypeAlreadyExistsException(CreditType type) {
    super("Already exists a credit modality with the type " + type, "CREDIT_TYPE_ALREADY_EXISTS", HttpStatus.CONFLICT);
  }
}
