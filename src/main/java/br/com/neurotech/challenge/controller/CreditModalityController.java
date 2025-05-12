package br.com.neurotech.challenge.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.dto.request.CreateCreditModalityRequest;
import br.com.neurotech.challenge.dto.request.FilterCreditModality;
import br.com.neurotech.challenge.dto.response.ErrorResponse;
import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.service.credit_modality.CreditModalityService;
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
@RequestMapping("/credit-modalities")
@Tag(name = "Credit Modality", description = "Credit Modality management API")
public class CreditModalityController {

  private CreditModalityService creditModalityService;

  public CreditModalityController(CreditModalityService creditModalityService) {
    this.creditModalityService = creditModalityService;
  }

  @PostMapping
  @Operation(summary = "Create a new credit modality", description = "Creates a new credit modality with the provided details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Credit modality created successfully", content = @Content(schema = @Schema(implementation = Void.class)), headers = @Header(name = "Location", description = "URL to retrieve the created credit modality", schema = @Schema(type = "string", example = Constants.BASE_URL
          + "/credit-modalities/1"))),
      @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "409", description = "Credit Modality for the specified credit type already exists", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<CreditModality> create(
      @Parameter(description = "Credit modality creation request", required = true) @Valid @RequestBody CreateCreditModalityRequest createRequest) {
    CreditModality creditModality = this.creditModalityService.create(createRequest);

    return ResponseEntity.created(URI.create(Constants.BASE_URL + "/credit-modalities/" + creditModality.getId()))
        .build();
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get credit modality by ID", description = "Retrieves credit modality details by the provided ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Credit modality found successfully", content = @Content(schema = @Schema(implementation = CreditModality.class))),
      @ApiResponse(responseCode = "404", description = "Credit modality not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<CreditModality> getById(
      @Parameter(description = "Credit modality ID", required = true, example = "1") @PathVariable("id") long id) {
    CreditModality creditModality = this.creditModalityService.getByIdOrThrow(id);
    return ResponseEntity.ok(creditModality);
  }

  @GetMapping("/filter")
  @Operation(summary = "Filter credit modalities", description = "Returns credit modalities matching the specified filter criteria")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Filtering successful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CreditModality.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid filter parameters", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<List<CreditModality>> filter(
      @Parameter(description = "Filter criteria", required = true) @Valid @RequestBody FilterCreditModality filter) {
    List<CreditModality> creditModalities = this.creditModalityService.filter(filter);

    return ResponseEntity.ok(creditModalities);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete credit modality", description = "Deletes a credit modality by the provided ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Credit modality deleted successfully", content = @Content(schema = @Schema(implementation = Void.class))),
      @ApiResponse(responseCode = "404", description = "Credit modality not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Void> delete(
      @Parameter(description = "Credit modality ID", required = true, example = "1") @PathVariable("id") long id) {
    this.creditModalityService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
