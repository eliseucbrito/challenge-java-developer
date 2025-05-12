package br.com.neurotech.challenge.dto.request;

import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckEligibleClientsRequest(
    @NotNull(message = "The minimum age must not be null") @Min(value = 18, message = "The minimum age must be greater than 18") Integer minAge,
    @NotNull(message = "The maximum age must not be null") @Positive(message = "The maximum age must be positive") Integer maxAge,
    @NotNull(message = "The credit type must not be null") CreditType creditType,
    @NotNull(message = "The vehicle model must not be null") VehicleModel vehicleModel) {

  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge == null || maxAge == null)
      return true;

    return minAge <= maxAge;
  }

}
