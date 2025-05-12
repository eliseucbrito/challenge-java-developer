package br.com.neurotech.challenge.util;

import java.lang.reflect.RecordComponent;
import java.util.Objects;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidatorUtils {

  private static final Validator validator;

  static {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  public static <T> ValidatedData<T> validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);

    return new ValidatedData<>(object, violations);
  }

  public static void throwViolations(Set<ConstraintViolation<?>> violations) {
    StringBuilder sb = new StringBuilder("Validation failed:\n");

    for (ConstraintViolation<?> violation : violations) {
      sb.append("- ")
          .append(violation.getPropertyPath())
          .append(": ")
          .append(violation.getMessage())
          .append("\n");
    }

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(sb.toString(), violations);
    }
  }

  /**
   * Returns true if any component of a Record is different from null.
   * 
   * @param record any Record
   */
  public static boolean hasAnyNonNullRecordComponent(Object record) {
    Class<?> cls = record.getClass();
    if (!cls.isRecord()) {
      throw new IllegalArgumentException("Classe " + cls.getName() + " is not a Record");
    }

    try {
      for (RecordComponent comp : cls.getRecordComponents()) {
        Object value = comp.getAccessor().invoke(record);
        if (Objects.nonNull(value)) {
          return true;
        }
      }
      return false;
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Fail while checking if Record has any non-null component", e);
    }
  }
}
