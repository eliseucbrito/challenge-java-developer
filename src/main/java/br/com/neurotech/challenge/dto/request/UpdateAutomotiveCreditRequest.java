package br.com.neurotech.challenge.dto.request;

import java.util.Objects;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.util.ValidatorUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateAutomotiveCreditRequest(
    @NotNull(message = "The vehicle model must not be null") VehicleModel vehicleModel,
    @Positive(message = "The minimum income must be positive") Double minIncome,
    @Positive(message = "The maximum income must be positive") Double maxIncome,
    @Min(value = 18, message = "The minimum age must be greater than 18") Integer minAge,
    @Positive(message = "The maximum age must be positive") Integer maxAge) {

  public AutomotiveCredit toUpdatedEntity(AutomotiveCredit originalCreditRule) {
    return new AutomotiveCredit(
        originalCreditRule.getId(),
        Objects.requireNonNullElseGet(vehicleModel(), originalCreditRule::getVehicleModel),
        Objects.requireNonNullElseGet(minIncome(), originalCreditRule::getMinIncome),
        Objects.requireNonNullElseGet(maxIncome(), originalCreditRule::getMaxIncome),
        Objects.requireNonNullElseGet(minAge(), originalCreditRule::getMinAge),
        Objects.requireNonNullElseGet(maxAge(), originalCreditRule::getMaxAge));
  }

  @AssertTrue(message = "Must has at least one field changed")
  public boolean hasAnyChange() {
    return ValidatorUtils.hasAnyNonNullRecordComponent(this);
  }

  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome == null || minIncome == null)
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
