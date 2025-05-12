package br.com.neurotech.challenge.dto.request;

import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(name = "CheckClientEligibilityRequest", description = "Verifies if a client is eligible for an automotive credit based on credit criteria")
public record CheckClientEligibilityRequest(
    @NotNull(message = "The client ID must not be null") @Min(value = 1, message = "The client ID must be greater than 0") @Schema(description = "Identifier of the client", example = "1", requiredMode = RequiredMode.REQUIRED) Long clientId,

    @NotNull(message = "The vehicle model must not be null") @Schema(description = "Vehicle model selected by the client", example = "HATCH", requiredMode = RequiredMode.REQUIRED) VehicleModel vehicleModel) {

}
