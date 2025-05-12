package br.com.neurotech.challenge.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public record ValidatedData<T>(T data, Set<ConstraintViolation<T>> violations) {

    public boolean isValid() {
        return violations.isEmpty();
    }

    public void throwIfInvalid() throws ConstraintViolationException {
        if (!isValid()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
