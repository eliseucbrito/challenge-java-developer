package br.com.neurotech.challenge.exception.common;

import org.springframework.http.HttpStatus;

import br.com.neurotech.challenge.exception.ApiException;

public class ResourceNotFoundException extends ApiException {
  public ResourceNotFoundException(String resourceType, String identifier) {
    super(String.format("%s not found with identifier: %s", resourceType, identifier), "RESOURCE_NOT_FOUND",
        HttpStatus.NOT_FOUND);
  }
}