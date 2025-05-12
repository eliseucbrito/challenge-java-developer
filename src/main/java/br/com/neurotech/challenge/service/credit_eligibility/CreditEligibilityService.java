package br.com.neurotech.challenge.service.credit_eligibility;

import java.util.List;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;

public interface CreditEligibilityService {

  /**
   * Method to find eligible clients for car credit
   *
   * @param minAge       the minimum age
   * @param maxAge       the maximum age
   * @param creditType   the credit type
   * @param vehicleModel the vehicle model
   * @return list of eligible clients
   */
  List<NeurotechClient> findEligibleClientsForCarCredit(
      int minAge,
      int maxAge,
      CreditType creditType,
      VehicleModel vehicleModel);

  /**
   * Method to check if a client is eligible for a specific vehicle model.
   *
   * @param clientId     the client ID
   * @param vehicleModel the vehicle model
   * @return true if the client is eligible for the vehicle model
   */
  boolean checkEligibility(long clientId, VehicleModel vehicleModel);
}
