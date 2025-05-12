package br.com.neurotech.challenge.entity.CreditModality;

import br.com.neurotech.challenge.entity.CreditDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "credit_modalities")
@Schema(name = "CreditModality", description = "Defines the type of credit and its associated interest rate with eligibility ranges")
public class CreditModality extends CreditDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier of the credit modality", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotNull(message = "The credit type must not be null")
  @Enumerated(EnumType.STRING)
  @Column(name = "credit_type", nullable = false, unique = true)
  @Schema(description = "Type of credit offered", example = "FIXED_INTEREST", requiredMode = RequiredMode.REQUIRED)
  private CreditType creditType;

  @Positive(message = "The interest rate must be positive")
  @Column(name = "interest_rate", nullable = true)
  @Schema(description = "Annual interest rate percentage for this credit modality", example = "3.5", requiredMode = RequiredMode.NOT_REQUIRED)
  private Double interestRate;

  public CreditModality(
      Long id,
      CreditType creditType,
      Double interestRate,
      Double minIncome,
      Double maxIncome,
      Integer minAge,
      Integer maxAge) {
    this.id = id;
    this.creditType = creditType;
    this.interestRate = interestRate;
    this.setMinIncome(minIncome);
    this.setMaxIncome(maxIncome);
    this.setMinAge(minAge);
    this.setMaxAge(maxAge);
  }
}
