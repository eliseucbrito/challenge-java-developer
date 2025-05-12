package br.com.neurotech.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "CreateAutomotiveCreditRequest", description = "Request payload to define eligibility criteria for an automotive credit")
public record CreateAutomotiveCreditRequest(

    @NotNull(message = "The vehicle model must not be null") @Schema(description = "Vehicle model to which this credit rule applies", example = "HATCH", requiredMode = RequiredMode.REQUIRED) VehicleModel vehicleModel,

    @NotNull(message = "The minimum income must not be null") @Min(value = 0, message = "The minimum income must be greater or equal to 0") @Schema(description = "Minimum required monthly income", example = "5000.0", requiredMode = RequiredMode.REQUIRED) Double minIncome,

    @Positive(message = "The maximum income must be positive") @Schema(description = "Maximum eligible monthly income (optional)", example = "15000.0") Double maxIncome,

    @Min(value = 18, message = "The minimum age must be greater than 18") @Schema(description = "Minimum applicant age", example = "18") Integer minAge,

    @Positive(message = "The maximum age must be positive") @Schema(description = "Maximum applicant age (optional)", example = "70") Integer maxAge) {

  public AutomotiveCredit toEntity() {
    return new AutomotiveCredit(
        null,
        vehicleModel(),
        minIncome(),
        maxIncome(),
        minAge(),
        maxAge());
  }

  @JsonIgnore
  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome == null)
      return true;

    return minIncome <= maxIncome;
  }

  @JsonIgnore
  @AssertTrue(message = "The minimum age must be less than or equal to maximum age")
  public boolean isAgeRangeValid() {
    if (minAge == null || maxAge == null)
      return true;

    return minAge <= maxAge;
  }
}
