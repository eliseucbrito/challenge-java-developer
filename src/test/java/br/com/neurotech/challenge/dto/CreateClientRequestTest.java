package br.com.neurotech.challenge.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.factories.TestCreateClientRequestFactory;
import br.com.neurotech.challenge.util.ValidatedData;
import br.com.neurotech.challenge.util.ValidatorUtils;

@SpringBootTest
class CreateClientRequestTest {

  @Test
  void testValidClientRequest() {
    CreateClientRequest request = TestCreateClientRequestFactory.valid();
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertTrue(validatedRequest.isValid());
  }

  @Test
  void testNameBlankValidation() {
    CreateClientRequest request = new CreateClientRequest("", LocalDate.now().minusYears(30), 5000.0);
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertEquals(1, validatedRequest.violations().size());
    assertEquals("Name must be a valid name", validatedRequest.violations().iterator().next().getMessage());
  }

  @Test
  void testNameNullValidation() {
    CreateClientRequest request = new CreateClientRequest(null, LocalDate.now().minusYears(30), 5000.0);
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertEquals(1, validatedRequest.violations().size());
    assertEquals("Name must be a valid name", validatedRequest.violations().iterator().next().getMessage());
  }

  @Test
  void testBirthDateInFutureValidation() {
    CreateClientRequest request = new CreateClientRequest("John Doe", LocalDate.now().plusDays(1), 5000.0);
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertEquals(1, validatedRequest.violations().size());
    assertEquals("The birth date must be in the past", validatedRequest.violations().iterator().next().getMessage());
  }

  @Test
  void testNegativeIncomeValidation() {
    CreateClientRequest request = new CreateClientRequest("John Doe", LocalDate.now().minusYears(30), -100.0);
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertEquals(1, validatedRequest.violations().size());
    assertEquals("Income must be a positive number", validatedRequest.violations().iterator().next().getMessage());
  }

  @Test
  void testZeroIncomeValidation() {
    CreateClientRequest request = new CreateClientRequest("John Doe", LocalDate.now().minusYears(30), -0.0);
    ValidatedData<CreateClientRequest> validatedRequest = ValidatorUtils.validate(request);

    assertEquals(1, validatedRequest.violations().size());
    assertEquals("Income must be a positive number", validatedRequest.violations().iterator().next().getMessage());
  }

  @Test
  void testToEntityMethod() {
    CreateClientRequest request = TestCreateClientRequestFactory.valid();
    NeurotechClient client = request.toEntity();

    assertNotNull(client);
    assertNull(client.getId());
    assertEquals("John Doe", client.getName());
    assertEquals(20, client.getAge());
    assertEquals(5000.0, client.getIncome());

    ValidatedData<NeurotechClient> validatedClient = ValidatorUtils.validate(client);
    assertTrue(validatedClient.isValid(), "The client entity must be valid");
  }
}
