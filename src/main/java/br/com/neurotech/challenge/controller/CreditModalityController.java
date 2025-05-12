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
import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.service.credit_modality.CreditModalityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/credit-modalities")
public class CreditModalityController {

  private CreditModalityService creditModalityService;

  public CreditModalityController(CreditModalityService creditModalityService) {
    this.creditModalityService = creditModalityService;
  }

  @PostMapping
  public ResponseEntity<CreditModality> create(@Valid @RequestBody CreateCreditModalityRequest createRequest) {
    CreditModality creditModality = this.creditModalityService.create(createRequest);

    return ResponseEntity.created(URI.create("http://localhost:8080/credit-modalities/" + creditModality.getId()))
        .build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreditModality> getById(@PathVariable("id") long id) {
    CreditModality creditModality = this.creditModalityService.getByIdOrThrow(id);
    return ResponseEntity.ok(creditModality);
  }

  @GetMapping("/filter")
  public ResponseEntity<List<CreditModality>> filter(@Valid @RequestBody FilterCreditModality filter) {
    List<CreditModality> creditModalities = this.creditModalityService.filter(filter);

    return ResponseEntity.ok(creditModalities);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    this.creditModalityService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
