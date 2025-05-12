package br.com.neurotech.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "CheckEligibleClientsRequest", description = "Request to check eligibility of clients by age range, credit type, and vehicle model")
public record CheckEligibleClientsRequest(

    @NotNull(message = "The minimum age must not be null") @Min(value = 18, message = "The minimum age must be greater than 18") @Schema(description = "Lower bound for client age (inclusive)", example = "23", requiredMode = RequiredMode.REQUIRED, minimum = "18") Integer minAge,

    @NotNull(message = "The maximum age must not be null") @Positive(message = "The maximum age must be positive") @Schema(description = "Upper bound for client age (inclusive)", example = "49", requiredMode = RequiredMode.REQUIRED) Integer maxAge,

    @NotNull(message = "The credit type must not be null") @Schema(description = "Type of credit to check eligibility for", example = "FIXED_INTEREST", requiredMode = RequiredMode.REQUIRED, allowableValues = {
        "FIXED_INTEREST", "VARIABLE_INTEREST", "PAYROLL_LOAN" }) CreditType creditType,

    @NotNull(message = "The vehicle model must not be null") @Schema(description = "Vehicle model to check eligibility for", example = "HATCH", requiredMode = RequiredMode.REQUIRED, allowableValues = {
        "HATCH", "SUV" }) VehicleModel vehicleModel

  ){

  @JsonIgnore
  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge == null || maxAge == null)
      return true;

    return minAge <= maxAge;
  }

}
