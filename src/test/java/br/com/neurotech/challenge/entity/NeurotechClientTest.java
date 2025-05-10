package br.com.neurotech.challenge.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    NeurotechClient client = TestClientFactory.createDefaultClient();

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertTrue(violations.isEmpty(), "Valid client should not have constraint violations");
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 1, 17 })
  void shouldValidateAgeLessThan18(int invalidAge) {
    NeurotechClient client = TestClientFactory.createDefaultClient();
    client.setAge(invalidAge);

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertEquals(1, violations.size(), "Should have exactly one violation");
    ConstraintViolation<NeurotechClient> violation = violations.iterator().next();
    assertEquals("age", violation.getPropertyPath().toString());
    assertEquals("The age must be at minimum 18 years", violation.getMessage());
  }

  @Test
  void shouldValidateAgeExactly18() {
    NeurotechClient client = TestClientFactory.createDefaultClient();
    client.setAge(18);

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertTrue(violations.isEmpty(), "Client with age exactly 18 should be valid");
  }

  @ParameterizedTest
  @ValueSource(doubles = { 0.0, -1.0, -100.0 })
  void shouldValidateNonPositiveIncome(double invalidIncome) {
    NeurotechClient client = TestClientFactory.createDefaultClient();
    client.setIncome(invalidIncome);

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);

    assertEquals(1, violations.size(), "Should have exactly one violation");
    ConstraintViolation<NeurotechClient> violation = violations.iterator().next();
    assertEquals("income", violation.getPropertyPath().toString());
    assertEquals("The income must be positive", violation.getMessage());
  }

  @Test
  void shouldValidateAllFieldsAtOnce() {
    NeurotechClient client = TestClientFactory.createInvalidClient();

    Set<ConstraintViolation<NeurotechClient>> violations = validator.validate(client);
    Set<String> violatedProperties = violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .collect(Collectors.toSet());

    assertEquals(3, violations.size(), "Should have three violations");
    assertTrue(violatedProperties.contains("name"), "Should have name violation");
    assertTrue(violatedProperties.contains("age"), "Should have age violation");
    assertTrue(violatedProperties.contains("income"), "Should have income violation");
  }
}