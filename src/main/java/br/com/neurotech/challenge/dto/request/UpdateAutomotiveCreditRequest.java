package br.com.neurotech.challenge.dto.request;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.util.ValidatorUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "UpdateAutomotiveCreditRequest", description = "Payload for updating an automotive credit rule. All fields are optional; at least one must be provided.")
public record UpdateAutomotiveCreditRequest(

    @Schema(description = "Vehicle model to update", example = "SUV", nullable = true) @NotNull(message = "The vehicle model must not be null") VehicleModel vehicleModel,

    @Schema(description = "New minimum required monthly income (optional)", example = "3000.0", nullable = true) @Positive(message = "The minimum income must be positive") Double minIncome,

    @Schema(description = "New maximum eligible monthly income (optional)", example = "12000.0", nullable = true) @Positive(message = "The maximum income must be positive") Double maxIncome,

    @Schema(description = "New minimum applicant age (optional)", example = "25", nullable = true) @Min(value = 18, message = "The minimum age must be greater than 18") Integer minAge,

    @Schema(description = "New maximum applicant age (optional)", example = "70", nullable = true) @Positive(message = "The maximum age must be positive") Integer maxAge

) {

  public AutomotiveCredit toUpdatedEntity(AutomotiveCredit originalCreditRule) {
    return new AutomotiveCredit(
        originalCreditRule.getId(),
        Objects.requireNonNullElseGet(vehicleModel(), originalCreditRule::getVehicleModel),
        Objects.requireNonNullElseGet(minIncome(), originalCreditRule::getMinIncome),
        Objects.requireNonNullElseGet(maxIncome(), originalCreditRule::getMaxIncome),
        Objects.requireNonNullElseGet(minAge(), originalCreditRule::getMinAge),
        Objects.requireNonNullElseGet(maxAge(), originalCreditRule::getMaxAge));
  }

  @JsonIgnore
  @AssertTrue(message = "Must has at least one field changed")
  public boolean hasAnyChange() {
    return ValidatorUtils.hasAnyNonNullRecordComponent(this);
  }

  @JsonIgnore
  @AssertTrue(message = "The minimum income must be less than or equal to maximum income")
  public boolean isIncomeRangeValid() {
    if (maxIncome == null || minIncome == null)
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
