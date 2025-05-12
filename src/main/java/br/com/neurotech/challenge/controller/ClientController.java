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
import br.com.neurotech.challenge.dto.response.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.service.client.ClientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody CreateClientRequest createRequest) {
    NeurotechClient client = this.clientService.create(createRequest);

    return ResponseEntity.created(URI.create("http://localhost:8080/clients/" + client.getId()))
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<NeurotechClientDTO> getById(@PathVariable("id") long id) {
    NeurotechClient client = this.clientService.getByIdOrThrow(id);

    return ResponseEntity.ok(NeurotechClientDTO.fromEntity(client));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    this.clientService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
