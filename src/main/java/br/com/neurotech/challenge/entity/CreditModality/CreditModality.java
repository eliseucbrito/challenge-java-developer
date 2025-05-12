package br.com.neurotech.challenge.entity.CreditModality;

import br.com.neurotech.challenge.entity.CreditDetails;
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
public class CreditModality extends CreditDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "The credit type must not be null")
  @Column(name = "credit_type", nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private CreditType creditType;

  @Positive(message = "The interest rate must be positive")
  @Column(name = "interest_rate", nullable = true, unique = false)
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
