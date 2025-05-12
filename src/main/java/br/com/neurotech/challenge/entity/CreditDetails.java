package br.com.neurotech.challenge.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class CreditDetails extends Auditable {

  @NotNull(message = "The minimum income must not be null")
  @Min(value = 0, message = "The minimum income must be greater or equal to 0")
  @Column(name = "min_income", nullable = false)
  @Schema(description = "Minimum required income", example = "2500.0", requiredMode = RequiredMode.REQUIRED, $comment = "The minimum income must be greater or equal to 0")
  private Double minIncome;

  @Positive(message = "The maximum income must be positive")
  @Column(name = "max_income", nullable = true)
  @Schema(description = "Maximum allowed income (optional)", example = "10000.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Double maxIncome;

  @Min(value = 18, message = "The minimun age must be greater than 18")
  @Column(name = "min_age", nullable = true)
  @Schema(description = "Minimum applicant age (optional)", example = "18", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Integer minAge;

  @Positive(message = "The maximum age must be positive")
  @Column(name = "max_age", nullable = true)
  @Schema(description = "Maximum applicant age (optional)", example = "65", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Integer maxAge;

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
