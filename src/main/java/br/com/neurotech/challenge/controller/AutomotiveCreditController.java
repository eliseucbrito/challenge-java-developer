package br.com.neurotech.challenge.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.service.automotive_credit.AutomotiveCreditService;
import br.com.neurotech.challenge.service.credit_eligibility.CreditEligibilityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/automotive-credits")
public class AutomotiveCreditController {

  private AutomotiveCreditService automotiveCreditService;
  private CreditEligibilityService creditEligibilityService;

  public AutomotiveCreditController(AutomotiveCreditService automotiveCreditService,
      CreditEligibilityService creditEligibilityService) {
    this.automotiveCreditService = automotiveCreditService;
    this.creditEligibilityService = creditEligibilityService;
  }

  @PostMapping
  public ResponseEntity<AutomotiveCredit> create(@Valid @RequestBody CreateAutomotiveCreditRequest createRequest) {
    AutomotiveCredit automotiveCredit = this.automotiveCreditService.create(createRequest);

    return ResponseEntity.created(URI.create("http://localhost:8080/automotive-credits/" + automotiveCredit.getId()))
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AutomotiveCredit> getById(@PathVariable("id") long id) {
    AutomotiveCredit automotiveCredit = this.automotiveCreditService.getByIdOrThrow(id);
    return ResponseEntity.ok(automotiveCredit);
  }

  @GetMapping("/filter")
  public ResponseEntity<List<AutomotiveCredit>> filter(@Valid @RequestBody FilterAutomotiveCreditRequest filter) {
    List<AutomotiveCredit> creditModalities = this.automotiveCreditService.filter(filter);

    return ResponseEntity.ok(creditModalities);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> update(@PathVariable("id") long id,
      @Valid @RequestBody UpdateAutomotiveCreditRequest updateRequest) {
    this.automotiveCreditService.update(id, updateRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    this.automotiveCreditService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/eligibility")
  public ResponseEntity<Boolean> checkClientEligibility(@Valid @RequestBody CheckClientEligibilityRequest request) {
    return ResponseEntity
        .ok(this.creditEligibilityService.checkEligibility(request.clientId(), request.vehicleModel()));
  }

  @GetMapping("/eligible-clients")
  public ResponseEntity<Map<String, Double>> findEligibleClients(
      @Valid @RequestBody CheckEligibleClientsRequest request) {
    List<NeurotechClient> clients = this.creditEligibilityService.findEligibleClientsForCarCredit(
        request.minAge(), request.maxAge(), request.creditType(), request.vehicleModel());

    return ResponseEntity.ok(clients.stream()
        .collect(Collectors.toMap(NeurotechClient::getName, NeurotechClient::getIncome)));
  }

}
