package br.com.neurotech.challenge.exceptions.common;

import br.com.neurotech.challenge.exceptions.BusinessException;

public class ResourceNotFoundException extends BusinessException {
  public ResourceNotFoundException(String resourceType, String identifier) {
    super(String.format("%s not found with identifier: %s", resourceType, identifier), "RESOURCE_NOT_FOUND");
  }
}