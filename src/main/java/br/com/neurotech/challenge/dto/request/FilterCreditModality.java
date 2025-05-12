package br.com.neurotech.challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import br.com.neurotech.challenge.util.ValidatorUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record FilterCreditModality(
    CreditType creditType,
    @Positive(message = "The minimum income must be positive") Double minIncome,
    @Positive(message = "The maximum income must be positive") Double maxIncome,
    @Min(value = 18, message = "The minimum age must be greater than 18") Integer minAge,
    @Positive(message = "The maximum age must be positive") Integer maxAge) {

  @JsonIgnore
  @AssertTrue(message = "Must has at least one filter")
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