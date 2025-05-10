package br.com.neurotech.challenge.service.credit;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.CreateCreditRuleRequest;
import br.com.neurotech.challenge.dto.request.UpdateCreditRuleRequest;
import br.com.neurotech.challenge.entity.CreditRule;
import br.com.neurotech.challenge.entity.VehicleModel;
import jakarta.validation.Valid;

@Service
public interface ICreditRuleService {

  /**
   * Save a credit rule.
   *
   * @param creditRule the credit rule to save
   * @return CreditRule the saved credit rule
   */
  CreditRule save(@Valid CreditRule creditRule);

  /**
   * Create a credit rule.
   *
   * @param creditRuleRequest the credit rule request
   * @return CreditRule the created credit rule
   */
  CreditRule create(@Valid CreateCreditRuleRequest creditRuleRequest);

  /**
   * Update a credit rule.
   *
   * @param id                      the credit rule ID
   * @param UpdateCreditRuleRequest the fields to update
   * @return CreditRule the updated credit rule
   */
  CreditRule update(long id, @Valid UpdateCreditRuleRequest updateFields);

  List<CreditRule> findCreditOptionsByVehicleModel(long clientId, VehicleModel model);

  /**
   * Verify if a client is eligible for credit for a specific vehicle model.
   *
   * @param id           the client ID
   * @param VehicleModel the vehicle model
   * @return list of available cars models
   */
  boolean checkCredit(long clientId, VehicleModel model);

}
