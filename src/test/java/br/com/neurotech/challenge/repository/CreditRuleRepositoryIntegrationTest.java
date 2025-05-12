package br.com.neurotech.challenge.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;

@DataJpaTest
class AutomotiveCreditRepositoryIntegrationTest {

  @Autowired
  private AutomotiveCreditRepository repo;

  @BeforeEach
  void setup() {
    repo.deleteAll();

    repo.save(new AutomotiveCredit(null, VehicleModel.HATCH, 1000.0, 5000.0, 18, 30));
    repo.save(new AutomotiveCredit(null, VehicleModel.SUV, 3000.0, 8000.0, 20, 50));
  }

  @Test
  void shouldReturnAllAutomotiveCredits() {
    List<AutomotiveCredit> results = repo.findAll();

    assertEquals(2, results.size(), "Should have two results");
  }

  @Test
  void whenAgeAndIncome_given_shouldReturnMatchingAndNullMax() {
    VehicleModel model = VehicleModel.HATCH;
    Integer age = 26;
    Double income = 2500.0;

    List<AutomotiveCredit> results = repo.filter(model, income, income, age, age);

    assertEquals(1, results.size(), "Should have one result");

    boolean has18to30 = results.stream()
        .anyMatch(cr -> cr.getMinAge() == 18 && Integer.valueOf(30).equals(cr.getMaxAge()));
    assertTrue(has18to30, "Should contain automotive credit 18–30");
  }
}
