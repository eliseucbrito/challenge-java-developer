package br.com.neurotech.challenge.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.factories.TestCreditRuleFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
class CreditRuleTest {

  private final Validator validator = Validation
      .buildDefaultValidatorFactory()
      .getValidator();

  @Test
  void shouldPassValidationWhenAllAttributesAreValid() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);

    assertTrue(violations.isEmpty(), "Valid credit rule should not have constraint violations");
  }

  @Test
  void shouldFailValidationWhenAttributesAreInvalid() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createInvalid();

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);
    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertFalse(violations.isEmpty(), "Invalid credit rule should have constraint violations");
    assertTrue(violatedProperties.contains("minIncome"), "Should have minIncome violation");
    assertTrue(violatedProperties.contains("maxIncome"), "Should have maxIncome violation");
    assertTrue(violatedProperties.contains("minAge"), "Should have minAge violation");
    assertTrue(violatedProperties.contains("maxAge"), "Should have maxAge violation");
  }

  @Test
  void shouldPassValidationWhenMaxValuesAreNull() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setMaxIncome(null);
    creditRule.setMaxAge(null);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);

    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertFalse(violatedProperties.contains("maxIncome"), "Should not have maxIncome violation");
    assertFalse(violatedProperties.contains("maxAge"), "Should not have maxAge violation");
  }

  @Test
  void shouldFailValidationWhenMinAgeIsNull() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setMinAge(null);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);

    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertFalse(violatedProperties.contains("minAge"), "Should not have minAge violation");
  }

  @Test
  void shouldFailValidationWhenIncomeRangeIsInvalid() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setMinIncome(5000.0);
    creditRule.setMaxIncome(1000.0);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);
    boolean hasIncomeRangeViolation = violations.stream()
        .anyMatch(v -> v.getMessage().contains("minimum income must be less than"));

    assertTrue(hasIncomeRangeViolation, "Should detect invalid income range");
  }

  @Test
  void shouldFailValidationWhenAgeRangeIsInvalid() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setMinAge(65);
    creditRule.setMaxAge(18);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);
    boolean hasAgeRangeViolation = violations.stream()
        .anyMatch(v -> v.getMessage().contains("minimum age must be less than"));

    assertTrue(hasAgeRangeViolation, "Should detect invalid age range");
  }

  @Test
  void shouldPassValidationWhenRangesAreEqual() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setMinIncome(1000.0);
    creditRule.setMaxIncome(1000.0);
    creditRule.setMinAge(30);
    creditRule.setMaxAge(30);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);

    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertFalse(violatedProperties.contains("minIncome"), "Should not have minIncome violation");
    assertFalse(violatedProperties.contains("maxIncome"), "Should not have maxIncome violation");
    assertFalse(violatedProperties.contains("minAge"), "Should not have minAge violation");
    assertFalse(violatedProperties.contains("maxAge"), "Should not have maxAge violation");
  }

  @Test
  void shouldFailValidationWhenVehicleModelIsNull() {

    AutomotiveCredit creditRule = TestCreditRuleFactory.createValid();
    creditRule.setVehicleModel(null);

    Set<ConstraintViolation<AutomotiveCredit>> violations = validator.validate(creditRule);

    assertEquals("The vehicle model must not be null",
        violations.stream()
            .filter(v -> v.getPropertyPath().toString().equals("vehicleModel"))
            .findFirst().get().getMessage());
  }

  @Test
  void testIsIncomeRangeValidMethod() {

    AutomotiveCredit creditRule = new AutomotiveCredit();

    creditRule.setMinIncome(1000.0);
    creditRule.setMaxIncome(null);
    assertTrue(creditRule.isIncomeRangeValid(), "Should be valid when maxIncome is null");

    creditRule.setMaxIncome(2000.0);
    assertTrue(creditRule.isIncomeRangeValid(), "Should be valid when minIncome <= maxIncome");

    creditRule.setMaxIncome(1000.0);
    assertTrue(creditRule.isIncomeRangeValid(), "Should be valid when minIncome = maxIncome");

    creditRule.setMaxIncome(500.0);
    assertFalse(creditRule.isIncomeRangeValid(), "Should be invalid when minIncome > maxIncome");
  }

  @Test
  void testIsAgeRangeValidMethod() {

    AutomotiveCredit creditRule = new AutomotiveCredit();

    creditRule.setMinAge(null);
    creditRule.setMaxAge(65);
    assertTrue(creditRule.isAgeRangeValid(), "Should be valid when minAge is null");

    creditRule.setMinAge(18);
    creditRule.setMaxAge(null);
    assertTrue(creditRule.isAgeRangeValid(), "Should be valid when maxAge is null");

    creditRule.setMinAge(null);
    creditRule.setMaxAge(null);
    assertTrue(creditRule.isAgeRangeValid(), "Should be valid when both age values are null");

    creditRule.setMinAge(18);
    creditRule.setMaxAge(65);
    assertTrue(creditRule.isAgeRangeValid(), "Should be valid when minAge <= maxAge");

    creditRule.setMinAge(30);
    creditRule.setMaxAge(30);
    assertTrue(creditRule.isAgeRangeValid(), "Should be valid when minAge = maxAge");

    creditRule.setMinAge(65);
    creditRule.setMaxAge(18);
    assertFalse(creditRule.isAgeRangeValid(), "Should be invalid when minAge > maxAge");
  }
}