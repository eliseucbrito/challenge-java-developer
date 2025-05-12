package br.com.neurotech.challenge.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.neurotech.challenge.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
    ErrorResponse error = new ErrorResponse(
        ex.getMessage(),
        ex.getCode(),
        ex.getStatus().value(),
        ex.getTime().toString());
    return new ResponseEntity<>(error, ex.getStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    String messages = ex.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));
    ErrorResponse error = new ErrorResponse(
        messages,
        "VALIDATION_ERROR",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ErrorResponse> handleInvalidFormat(InvalidFormatException ex) {
    String fieldName = ex.getPath().stream()
        .map(ref -> ref.getFieldName())
        .findFirst()
        .orElse("value");
    String targetType = ex.getTargetType() != null ? ex.getTargetType().getSimpleName() : "expected type";
    String message = String.format("Field '%s' must be of type %s", fieldName, targetType);
    ErrorResponse error = new ErrorResponse(
        message,
        "INVALID_FORMAT",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    String messages = ex.getConstraintViolations().stream()
        .map(v -> v.getMessage())
        .collect(Collectors.joining(", "));
    ErrorResponse error = new ErrorResponse(
        messages,
        "CONSTRAINT_VIOLATION",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    ErrorResponse error = new ErrorResponse(
        "Malformed JSON request",
        "MALFORMED_JSON",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message = String.format("Parameter '%s' must be of type %s", ex.getName(),
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
    ErrorResponse error = new ErrorResponse(
        message,
        "TYPE_MISMATCH",
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllUncaught(Exception ex, WebRequest request) {
    ErrorResponse error = new ErrorResponse(
        ex.getMessage(),
        "INTERNAL_ERROR",
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now().toString());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
