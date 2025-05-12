package br.com.neurotech.challenge.service.automotive_credit;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.CreateAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.FilterAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.UpdateAutomotiveCreditRequest;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.exception.common.ConflitException;
import br.com.neurotech.challenge.exception.credit.AutomotiveCreditNotFoundException;
import br.com.neurotech.challenge.repository.AutomotiveCreditRepository;
import br.com.neurotech.challenge.service.client.ClientService;
import jakarta.validation.Valid;

@Service
public class AutomotiveCreditServiceImpl implements AutomotiveCreditService {

  private ClientService clientService;
  private AutomotiveCreditRepository automotiveCreditRepository;

  public AutomotiveCreditServiceImpl(ClientService clientService,
      AutomotiveCreditRepository automotiveCreditRepository) {
    this.clientService = clientService;
    this.automotiveCreditRepository = automotiveCreditRepository;
  }

  @Override
  public AutomotiveCredit save(@Valid AutomotiveCredit creditRule) {
    return this.automotiveCreditRepository.save(creditRule);
  }

  @Override
  public AutomotiveCredit create(@Valid CreateAutomotiveCreditRequest creditRuleRequest) {
    AutomotiveCredit creditRule = creditRuleRequest.toEntity();

    return this.automotiveCreditRepository.save(creditRule);
  }

  @Override
  public AutomotiveCredit update(long id, @Valid UpdateAutomotiveCreditRequest updateRequest) {
    AutomotiveCredit original = this.automotiveCreditRepository.findById(id)
        .orElseThrow(() -> new AutomotiveCreditNotFoundException(Long.toString(id)));

    AutomotiveCredit updatedCreditRule = updateRequest.toUpdatedEntity(original);

    return this.automotiveCreditRepository.save(updatedCreditRule);
  }

  @Override
  public List<AutomotiveCredit> filter(FilterAutomotiveCreditRequest filter) {
    return this.automotiveCreditRepository.filter(filter.vehicleModel(), filter.minIncome(), filter.maxIncome(),
        filter.minAge(), filter.maxAge());
  }

  @Override
  public void delete(long id) {
    this.getByIdOrThrow(id);
    this.automotiveCreditRepository.deleteById(id);
  }

  @Override
  public Optional<AutomotiveCredit> getById(long id) {
    return this.automotiveCreditRepository.findById(id);
  }

  @Override
  public AutomotiveCredit getByIdOrThrow(long id) throws AutomotiveCreditNotFoundException {
    return this.automotiveCreditRepository.findById(id)
        .orElseThrow(() -> new AutomotiveCreditNotFoundException(Long.toString(id)));
  }

  @Override
  public AutomotiveCredit getByModel(VehicleModel model) {
    List<AutomotiveCredit> result = this.automotiveCreditRepository.filter(model, null, null, null, null);

    if (result.toArray().length > 1) {
      throw new ConflitException("More than one automotive credit found for model " + model.name(),
          "AUTOMOTIVE_CREDIT_DUPLICATED");
    }

    return Optional.of(result.get(0)).orElseThrow(() -> new AutomotiveCreditNotFoundException(model.name()));
  }
}
