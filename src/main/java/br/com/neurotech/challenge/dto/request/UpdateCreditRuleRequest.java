package br.com.neurotech.challenge.dto.request;

import java.util.Optional;

import br.com.neurotech.challenge.entity.VehicleModel;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateCreditRuleRequest(
    @NotNull(message = "The vehicle model must not be null") Optional<VehicleModel> vehicleModel,
    @Positive(message = "The minimum income must be positive") Optional<Double> minIncome,
    @Positive(message = "The maximum income must be positive") Optional<Double> maxIncome,
    @Min(value = 18, message = "The minimum age must be greater than 18") Optional<Integer> minAge,
    @Positive(message = "The maximum age must be positive") Optional<Integer> maxAge) {

  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome.isEmpty() || minIncome.isEmpty())
      return true;
    return minIncome.get() <= maxIncome.get();
  }

  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge.isEmpty() || maxAge.isEmpty())
      return true;

    return minAge.get() <= maxAge.get();
  }
}
