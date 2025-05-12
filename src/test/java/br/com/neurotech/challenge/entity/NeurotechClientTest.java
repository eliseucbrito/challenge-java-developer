package br.com.neurotech.challenge.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.neurotech.challenge.factories.TestClientFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootTest
class NeurotechClientTest {

  private final Validator validator = Validation
      .buildDefaultValidatorFactory()
      .getValidator();

  @Test
  void shouldNotHaveViolationsWhenClientIsValid() {
    NeurotechClient client = TestClientFactory.valid();

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertTrue(violations.isEmpty(), "Valid client should not have constraint violations");
  }

  @Test
  void shouldValidateAgeExactly18() {
    NeurotechClient client = TestClientFactory.valid();
    client.setBirthDate(LocalDate.now().minusYears(18));

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertTrue(violations.isEmpty(), "Client with age exactly 18 should be valid");
  }

  void shouldValidateNonPositiveIncome() {
    NeurotechClient client = TestClientFactory.valid();
    client.setIncome(-100.0);

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertEquals(1, violations.size(), "Should have exactly one violation");
    ConstraintViolation<NeurotechClient> violation = violations.iterator().next();
    assertEquals("income", violation.getPropertyPath().toString());
    assertEquals("The income must be positive", violation.getMessage());
  }

  @Test
  void shouldValidateAllFieldsAtOnce() {
    NeurotechClient client = TestClientFactory.invalid();

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);
    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertEquals(4, violations.size(), "Should have three violations");
    assertTrue(violatedProperties.contains("name"), "Should have name violation");
    assertTrue(violatedProperties.contains("birthDate"), "Should have birthDate violation");
    assertTrue(violatedProperties.contains("age"), "Should have age violation");
    assertTrue(violatedProperties.contains("income"), "Should have income violation");
  }
}