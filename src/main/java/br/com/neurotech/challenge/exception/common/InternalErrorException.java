package br.com.neurotech.challenge.exception.common;

import org.springframework.http.HttpStatus;

import br.com.neurotech.challenge.exception.ApiException;

public class InternalErrorException extends ApiException {
  public InternalErrorException(String message) {
    super(message, "INTERNAL_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
