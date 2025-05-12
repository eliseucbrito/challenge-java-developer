package br.com.neurotech.challenge.entity;

import java.time.LocalDate;
import java.time.Period;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "neurotech_clients")
@Schema(name = "NeurotechClient", description = "A client of Neurotech with personal details and computed age")
public class NeurotechClient extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier of the client", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Name must not be null")
  @Column(nullable = false)
  @Schema(description = "Full name of the client", example = "Alice Johnson", requiredMode = RequiredMode.REQUIRED)
  private String name;

  @NotNull(message = "The birth date must not be null")
  @Past(message = "The birth date must be in the past")
  @Column(name = "birth_date", nullable = false)
  @Schema(description = "Client’s birth date (ISO format)", example = "1995-08-20", requiredMode = RequiredMode.REQUIRED, format = "date")
  private LocalDate birthDate;

  @NotNull(message = "The income must not be null")
  @Positive(message = "The income must be positive")
  @Column(nullable = false)
  @Schema(description = "Monthly income of the client", example = "3500.00", requiredMode = RequiredMode.REQUIRED)
  private Double income;

  @Schema(description = "Computed age of the client in years", example = "29", accessMode = Schema.AccessMode.READ_ONLY)
  public Integer getAge() {
    return Period.between(this.birthDate, LocalDate.now()).getYears();
  }
}
