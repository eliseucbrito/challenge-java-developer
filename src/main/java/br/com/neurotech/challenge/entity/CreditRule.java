package br.com.neurotech.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "credit_rules")
public class CreditRule extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "The vehicle model must not be null")
  @Column(name = "vehicle_model", nullable = false)
  private VehicleModel vehicleModel;

  @Positive(message = "The minimum income must be positive")
  @Column(name = "min_income", nullable = false)
  private Double minIncome;

  @Positive(message = "The maximum income must be positive")
  @Column(name = "max_income", nullable = true)
  private Double maxIncome;

  @Min(value = 18, message = "The minimun age must be greater than 18")
  @Column(name = "min_age", nullable = true)
  private Integer minAge;

  @Positive(message = "The maximum age must be positive")
  @Column(name = "max_age", nullable = true)
  private Integer maxAge;

  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome == null)
      return true;
    return minIncome <= maxIncome;
  }

  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge == null || maxAge == null)
      return true;
    return minAge <= maxAge;
  }
}
