package br.com.neurotech.challenge.entity.AutomotiveCredit;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "automotive_credits")
public class AutomotiveCredit extends CreditDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "The vehicle model must not be null")
  @Column(name = "vehicle_model", unique = true, nullable = false)
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
