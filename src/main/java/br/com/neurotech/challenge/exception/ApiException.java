package br.com.neurotech.challenge.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {

  private final String message;
  private final String code;
  private final HttpStatus status;
  private final LocalDateTime time;

  public ApiException(String message, String code, HttpStatus status) {
    super(message);
    this.message = message;
    this.code = code;
    this.status = status;
    this.time = LocalDateTime.now();
  }

}