package br.com.neurotech.challenge.entity;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
public class NeurotechClient extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name must not be null")
  private String name;

  @Past(message = "The birth date must be in the past")
  @NotNull(message = "The birth date must not be null")
  private LocalDate birthDate;

  @Positive(message = "The income must be positive")
  @NotNull(message = "The income must not be null")
  private Double income;

  @Min(value = 18, message = "The age must be at minimum 18 years")
  public Integer getAge() {
    return Period.between(this.birthDate, LocalDate.now()).getYears();
  }
}
