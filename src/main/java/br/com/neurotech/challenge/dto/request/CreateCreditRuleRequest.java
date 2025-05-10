package br.com.neurotech.challenge.dto.request;

import java.util.Optional;

import br.com.neurotech.challenge.entity.CreditRule;
import br.com.neurotech.challenge.entity.VehicleModel;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCreditRuleRequest(
    @NotNull(message = "The vehicle model must not be null") VehicleModel vehicleModel,
    @Positive(message = "The minimum income must be positive") Double minIncome,
    @Positive(message = "The maximum income must be positive") Optional<Double> maxIncome,
    @Min(value = 18, message = "The minimum age must be greater than 18") Optional<Integer> minAge,
    @Positive(message = "The maximum age must be positive") Optional<Integer> maxAge) {

  public CreditRule toEntity() {
    return new CreditRule(
        null,
        this.vehicleModel,
        this.minIncome,
        this.maxIncome.orElse(null),
        this.minAge.orElse(null),
        this.maxAge.orElse(null));
  }

  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome.isEmpty())
      return true;

    return minIncome <= maxIncome.get();
  }

  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge.isEmpty() || maxAge.isEmpty())
      return true;

    return minAge.get() <= maxAge.get();
  }
}
