package br.com.neurotech.challenge.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


@SpringBootTest
public class ClientTest {

  private final Validator validator = Validation
          .buildDefaultValidatorFactory()
          .getValidator();

  @Test
  void  shouldThrowExceptionsWhenCreateClientWithInvalidProps() {
    NeurotechClient client = NeurotechClient.builder().name("").age(11).income(-10.0).build();

    Set<ConstraintViolation<NeurotechClient>> violations =
            validator.validate(client);

    assertEquals(3, violations.size(),
            "There should be 3 constraint violations");
  }
}
