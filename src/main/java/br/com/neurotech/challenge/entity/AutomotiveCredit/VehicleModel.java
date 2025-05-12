package br.com.neurotech.challenge.entity.AutomotiveCredit;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "VehicleModel", description = "Allowed values for vehicle model", example = "HATCH", enumAsRef = true)
public enum VehicleModel {
  HATCH, SUV
}
