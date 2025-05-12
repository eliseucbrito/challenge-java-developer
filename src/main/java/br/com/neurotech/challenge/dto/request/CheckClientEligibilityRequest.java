package br.com.neurotech.challenge.dto.request;

import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CheckClientEligibilityRequest(
    @NotNull(message = "The client ID must not be null") @Min(value = 1, message = "The client ID must be greater than 0") Long clientId,
    @NotNull(message = "The vehicle model must not be null") VehicleModel vehicleModel) {

}
