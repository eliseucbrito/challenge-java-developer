package br.com.neurotech.challenge.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.dto.response.ErrorResponse;
import br.com.neurotech.challenge.dto.response.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import br.com.neurotech.challenge.util.Constants;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client", description = "Client management API")
public class ClientController {

  private ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  @Operation(
    summary = "Create a new client",
    description = "Creates a new client in the system with the provided details. On success, returns a Location header with the URL to retrieve the created client."
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Client created successfully",
      content = @Content(schema = @Schema(implementation = Void.class)),
      headers = @Header(
        name = "Location",
        description = "URL to retrieve the created client",
        schema = @Schema(type = "string", example = Constants.BASE_URL + "/clients/1")
      )
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Invalid input data provided",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Internal server error",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
  })
  public ResponseEntity<?> create(
    @Parameter(description = "Client creation request body", required = true)
    @Valid @RequestBody CreateClientRequest createRequest
  ) {
    NeurotechClient client = this.clientService.create(createRequest);

    return ResponseEntity.created(URI.create(Constants.BASE_URL + "/clients/" + client.getId()))
        .build();
  }

  @GetMapping("/{id}")
  @Operation(
    summary = "Get client by ID",
    description = "Retrieves client details by the provided client ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Client found successfully",
      content = @Content(schema = @Schema(implementation = NeurotechClientDTO.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Client not found",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Internal server error",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
  })
  public ResponseEntity<NeurotechClientDTO> getById(
    @Parameter(description = "Client ID", required = true, example = "1")
    @PathVariable("id") long id
  ) {
    NeurotechClient client = this.clientService.getByIdOrThrow(id);

    return ResponseEntity.ok(NeurotechClientDTO.fromEntity(client));
  }

  @DeleteMapping("/{id}")
  @Operation(
    summary = "Delete client",
    description = "Deletes a client from the system by the provided client ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "204",
      description = "Client successfully deleted",
      content = @Content(schema = @Schema(implementation = Void.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Client not found",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Internal server error",
      content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
  })
  public ResponseEntity<Void> delete(
    @Parameter(description = "Client ID", required = true, example = "1")
    @PathVariable("id") long id
  ) {
    this.clientService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
