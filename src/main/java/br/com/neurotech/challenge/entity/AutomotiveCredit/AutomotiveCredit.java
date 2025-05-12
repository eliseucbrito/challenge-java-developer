package br.com.neurotech.challenge.entity.AutomotiveCredit;

import br.com.neurotech.challenge.entity.CreditDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "automotive_credits")
@Schema(name = "AutomotiveCredit", description = "Defines credit eligibility criteria for a particular vehicle model")
public class AutomotiveCredit extends CreditDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier of the automotive credit rule", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotNull(message = "The vehicle model must not be null")
  @Column(name = "vehicle_model", nullable = false)
  @Enumerated(EnumType.STRING)
  private VehicleModel vehicleModel;

  public AutomotiveCredit(
      Long id,
      VehicleModel vehicleModel,
      Double minIncome,
      Double maxIncome,
      Integer minAge,
      Integer maxAge) {
    this.id = id;
    this.vehicleModel = vehicleModel;
    this.setMinIncome(minIncome);
    this.setMaxIncome(maxIncome);
    this.setMinAge(minAge);
    this.setMaxAge(maxAge);
  }

}
