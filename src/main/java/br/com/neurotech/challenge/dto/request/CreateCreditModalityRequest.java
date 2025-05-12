package br.com.neurotech.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "CreditModalityRequest", description = "Payload to create a new credit modality")
public record CreateCreditModalityRequest(

    @NotNull(message = "The credit type must not be null") @Schema(description = "Type of credit offered", example = "FIXED_INTEREST", requiredMode = RequiredMode.REQUIRED) CreditType creditType,

    @NotNull(message = "The interest rate must not be null") @Positive(message = "The interest rate must be positive") @Schema(description = "Annual interest rate percentage", example = "5.0", requiredMode = RequiredMode.NOT_REQUIRED) Double interestRate,

    @NotNull(message = "The minimum income must not be null") @Min(value = 0, message = "The minimum income must be greater or equal to 0") @Schema(description = "Minimum required income", example = "0", requiredMode = RequiredMode.REQUIRED) Double minIncome,

    @Positive(message = "The maximum income must be positive") @Schema(description = "Maximum allowed income (optional)", example = "90000.0", requiredMode = RequiredMode.NOT_REQUIRED) Double maxIncome,

    @Min(value = 18, message = "The minimum age must be at least 18") @Schema(description = "Minimum applicant age", example = "18", requiredMode = RequiredMode.NOT_REQUIRED) Integer minAge,

    @Positive(message = "The maximum age must be positive") @Schema(description = "Maximum applicant age (optional)", example = "25", requiredMode = RequiredMode.NOT_REQUIRED) Integer maxAge) {

  public CreditModality toEntity() {
    return new CreditModality(null, creditType(), interestRate(), minIncome(), maxIncome(), minAge(), maxAge());
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
