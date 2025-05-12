package br.com.neurotech.challenge.service.automotive_credit;

import java.util.List;
import java.util.Optional;

import br.com.neurotech.challenge.dto.request.CreateAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.FilterAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.UpdateAutomotiveCreditRequest;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.exception.credit.AutomotiveCreditNotFoundException;
import jakarta.validation.Valid;

public interface AutomotiveCreditService {

  /**
   * Save a automotive credit.
   *
   * @param entity the entity to save {@link AutomotiveCredit}
   * @return the saved automotive credit
   */
  AutomotiveCredit save(@Valid AutomotiveCredit credit);

  /**
   * Create a automotive credit.
   *
   * @param createRequest the automotive credit request
   *                      {@link CreateAutomotiveCreditRequest}
   * @return the created automotive credit
   */
  AutomotiveCredit create(@Valid CreateAutomotiveCreditRequest createRequest);

  /**
   * Update a automotive credit.
   *
   * @param id                            the credit rule ID
   * @param UpdateAutomotiveCreditRequest the fields to update
   * @return the updated automotive credit
   */
  AutomotiveCredit update(long id, @Valid UpdateAutomotiveCreditRequest updateFields);

  /**
   * Filter credit rules by criteria.
   *
   * @param filter must has at least one filter
   *               {@link FilterAutomotiveCreditRequest}
   * @return list of available automotive credits
   */
  List<AutomotiveCredit> filter(FilterAutomotiveCreditRequest filter);

  /**
   * Method to get a automotive credit by ID
   *
   * @param id must not be null
   * @return the automotive credit with the given id or Optional#empty() if none
   *         found.
   */
  Optional<AutomotiveCredit> getById(long id);

  /**
   * Method to get a automotive credit by model
   *
   * @param model must not be null
   * @return the automotive credit with the given model
   * @throws AutomotiveCreditNotFoundException
   */
  AutomotiveCredit getByModel(VehicleModel model);

  /**
   * Method to get a automotive credit by ID or throw an exception
   *
   * @param id must not be null
   * @return the automotive credit with the given id
   * @throws AutomotiveCreditNotFoundException
   */
  AutomotiveCredit getByIdOrThrow(long id) throws AutomotiveCreditNotFoundException;

  /**
   * Delete a automotive credit.
   *
   * @param id the automotive credit ID to delete
   */
  void delete(long id);
}
