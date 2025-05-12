package br.com.neurotech.challenge.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.dto.request.CheckClientEligibilityRequest;
import br.com.neurotech.challenge.dto.request.CheckEligibleClientsRequest;
import br.com.neurotech.challenge.dto.request.CreateAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.FilterAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.UpdateAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.response.ErrorResponse;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.service.automotive_credit.AutomotiveCreditService;
import br.com.neurotech.challenge.service.credit_eligibility.CreditEligibilityService;
import br.com.neurotech.challenge.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/automotive-credits")
@Tag(name = "Automotive Credit", description = "Automotive Credit management and eligibility API")
public class AutomotiveCreditController {

  private AutomotiveCreditService automotiveCreditService;
  private CreditEligibilityService creditEligibilityService;

  public AutomotiveCreditController(AutomotiveCreditService automotiveCreditService,
      CreditEligibilityService creditEligibilityService) {
    this.automotiveCreditService = automotiveCreditService;
    this.creditEligibilityService = creditEligibilityService;
  }

  @PostMapping
  @Operation(summary = "Create a new automotive credit", description = "Creates a new automotive credit record with the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Automotive credit created successfully", content = @Content(schema = @Schema(implementation = Void.class)), headers = @Header(name = "Location", description = "URL to retrieve the created automotive credit", schema = @Schema(type = "string", example = Constants.BASE_URL
          + "/automotive-credits/1"))),
      @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "409", description = "Automotive credit for the specified vehicle model already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<AutomotiveCredit> create(
      @Parameter(description = "Automotive credit creation request", required = true) @Valid @RequestBody CreateAutomotiveCreditRequest createRequest) {
    AutomotiveCredit automotiveCredit = this.automotiveCreditService.create(createRequest);

    return ResponseEntity.created(URI.create(Constants.BASE_URL + "/automotive-credits/" + automotiveCredit.getId()))
        .build();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get automotive credit by ID", description = "Retrieves automotive credit details by the provided ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Automotive credit found successfully", content = @Content(schema = @Schema(implementation = AutomotiveCredit.class))),
      @ApiResponse(responseCode = "404", description = "Automotive credit not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<AutomotiveCredit> getById(
      @Parameter(description = "Automotive credit ID", required = true, example = "1") @PathVariable("id") long id) {
    AutomotiveCredit automotiveCredit = this.automotiveCreditService.getByIdOrThrow(id);
    return ResponseEntity.ok(automotiveCredit);
  }

  @GetMapping("/filter")
  @Operation(summary = "Filter automotive credits", description = "Returns automotive credits matching the specified filter criteria")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Filtering successful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AutomotiveCredit.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid filter parameters", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<List<AutomotiveCredit>> filter(
      @Parameter(description = "Filter criteria", required = true) @Valid @RequestBody FilterAutomotiveCreditRequest filter) {
    List<AutomotiveCredit> creditModalities = this.automotiveCreditService.filter(filter);

    return ResponseEntity.ok(creditModalities);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update automotive credit", description = "Updates an existing automotive credit with the provided information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Automotive credit updated successfully", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Automotive credit not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Void> update(
      @Parameter(description = "Automotive credit ID", required = true, example = "1") @PathVariable("id") long id,
      @Parameter(description = "Update request body", required = true) @Valid @RequestBody UpdateAutomotiveCreditRequest updateRequest) {
    this.automotiveCreditService.update(id, updateRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete automotive credit", description = "Deletes an automotive credit by the provided ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Automotive credit deleted successfully", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "404", description = "Automotive credit not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Void> delete(
      @Parameter(description = "Automotive credit ID", required = true, example = "1") @PathVariable("id") long id) {
    this.automotiveCreditService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/eligibility")
  @Operation(summary = "Check client eligibility for automotive credit", description = "Checks if a client is eligible for automotive credit for the specified vehicle model")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Eligibility check completed", content = @Content(schema = @Schema(implementation = Boolean.class, example = "true"))),
      @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Boolean> checkClientEligibility(
      @Valid @ModelAttribute CheckClientEligibilityRequest request) {
    return ResponseEntity
        .ok(this.creditEligibilityService.checkEligibility(request.clientId(), request.vehicleModel()));
  }

  @GetMapping("/eligible-clients")
  @Operation(summary = "Find eligible clients for automotive credit", description = "Returns a list of clients eligible for automotive credit based on specified criteria")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Eligible clients found", content = @Content(schema = @Schema(implementation = Map.class, example = "{\"John Doe\":5000.00,\"Jane Smith\":7500.00}"))),
      @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Map<String, Double>> findEligibleClients(
      @Parameter(description = "Eligible clients search criteria", required = true) @Valid @ModelAttribute CheckEligibleClientsRequest request) {
    List<NeurotechClient> clients = this.creditEligibilityService.findEligibleClientsForCarCredit(
        request.minAge(), request.maxAge(), request.creditType(), request.vehicleModel());

    return ResponseEntity.ok(clients.stream()
        .collect(Collectors.toMap(NeurotechClient::getName, NeurotechClient::getIncome)));
  }
}
