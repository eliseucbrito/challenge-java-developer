package br.com.neurotech.challenge.dto.request;

import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCreditModalityRequest(
    @NotNull(message = "The credit type must not be null") CreditType creditType,
    @NotNull(message = "The interest rate must not be null") @Positive(message = "The interest rate must be positive") Double interestRate,
    @NotNull(message = "The minimum income must not be null") @Min(value = 0, message = "The minimum income must be greater or equal to 0") Double minIncome,

    @Positive(message = "The maximum income must be positive") Double maxIncome,
    @Min(value = 18, message = "The minimun age must be greater than 18") Integer minAge,
    @Positive(message = "The maximum age must be positive") Integer maxAge

) {

  public CreditModality toEntity() {
    return new CreditModality(null, creditType(), interestRate(), minIncome(), maxIncome(), minAge(), maxAge());
  }

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
