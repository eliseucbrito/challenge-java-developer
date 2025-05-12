package br.com.neurotech.challenge.exception.common;

import org.springframework.http.HttpStatus;

import br.com.neurotech.challenge.exception.ApiException;

public class ConflitException extends ApiException {
  public ConflitException(String message, String code) {
    super(message, code, HttpStatus.CONFLICT);
  }
}
