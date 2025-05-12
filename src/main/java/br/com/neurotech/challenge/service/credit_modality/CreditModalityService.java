package br.com.neurotech.challenge.service.credit_modality;

import java.util.List;
import java.util.Optional;

import br.com.neurotech.challenge.dto.request.CreateCreditModalityRequest;
import br.com.neurotech.challenge.dto.request.FilterCreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.exception.credit_modality.CreditModalityNotFoundException;
import jakarta.validation.Valid;

public interface CreditModalityService {

  /**
   * Save a credit modality.
   *
   * @param entity the entity to save {@link CreditModality}
   * @return the savedcredit modality
   */
  CreditModality save(@Valid CreditModality credit);

  /**
   * Create a credit modality.
   *
   * @param createRequest the credit modality request
   *                      {@link CreateCreditModalityRequest}
   * @return the created credit modality
   */
  CreditModality create(@Valid CreateCreditModalityRequest createRequest);

  /**
   * Filter credit modality by criteria.
   *
   * @param filters must has at least one filter
   *                {@link FilterCreditModality}
   * @return list of available credit modalities that match the criteria
   */
  List<CreditModality> filter(@Valid FilterCreditModality filters);

  /**
   * Method to get a credit modality by ID
   *
   * @param id must not be null
   * @return the credit modality with the given id or Optional#empty() if none
   *         found.
   */
  Optional<CreditModality> getById(long id);

  /**
   * Method to get a credit modality by ID or throw an exception
   *
   * @param id must not be null
   * @return the credit modality with the given id
   * @throws CreditModalityNotFoundException
   */
  CreditModality getByIdOrThrow(long id) throws CreditModalityNotFoundException;

  /**
   * Delete a credit modality.
   *
   * @param id the credit modality ID to delete
   */
  void delete(long id);
}