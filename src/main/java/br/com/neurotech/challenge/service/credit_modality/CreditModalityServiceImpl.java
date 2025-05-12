package br.com.neurotech.challenge.service.credit_modality;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.CreateCreditModalityRequest;
import br.com.neurotech.challenge.dto.request.FilterCreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.exception.credit_modality.CreditModalityNotFoundException;
import br.com.neurotech.challenge.exception.credit_modality.CreditTypeAlreadyExistsException;
import br.com.neurotech.challenge.repository.CreditModalityRepository;
import jakarta.validation.Valid;

@Service
public class CreditModalityServiceImpl implements CreditModalityService {

  private final CreditModalityRepository creditModalityRepository;

  public CreditModalityServiceImpl(CreditModalityRepository creditModalityRepository) {
    this.creditModalityRepository = creditModalityRepository;
  }

  @Override
  public CreditModality save(@Valid CreditModality credit) {
    return this.creditModalityRepository.save(credit);
  }

  @Override
  public CreditModality create(@Valid CreateCreditModalityRequest createRequest) {
    List<CreditModality> conflits = this
        .filter(new FilterCreditModality(createRequest.creditType(), null, null, null, null));

    if (!conflits.isEmpty()) {
      throw new CreditTypeAlreadyExistsException(createRequest.creditType());
    }

    CreditModality creditModality = createRequest.toEntity();

    return this.save(creditModality);
  }

  @Override
  public List<CreditModality> filter(FilterCreditModality filters) {
    return this.creditModalityRepository.filter(filters.creditType(), filters.minIncome(), filters.maxIncome(),
        filters.minAge(), filters.maxAge());
  }

  @Override
  public void delete(long id) {
    this.getByIdOrThrow(id);
    this.creditModalityRepository.deleteById(id);
  }

  @Override
  public Optional<CreditModality> getById(long id) {
    return this.creditModalityRepository.findById(id);
  }

  @Override
  public CreditModality getByIdOrThrow(long id) throws CreditModalityNotFoundException {
    return this.creditModalityRepository.findById(id)
        .orElseThrow(() -> new CreditModalityNotFoundException(Long.toString(id)));
  }

}
