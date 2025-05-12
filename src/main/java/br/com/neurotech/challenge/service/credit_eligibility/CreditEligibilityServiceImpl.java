package br.com.neurotech.challenge.service.credit_eligibility;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.FilterCreditModality;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.entity.CreditModality.CreditModality;
import br.com.neurotech.challenge.entity.CreditModality.CreditType;
import br.com.neurotech.challenge.exception.common.ConflitException;
import br.com.neurotech.challenge.exception.credit_modality.CreditModalityNotFoundException;
import br.com.neurotech.challenge.service.automotive_credit.AutomotiveCreditService;
import br.com.neurotech.challenge.service.client.ClientService;
import br.com.neurotech.challenge.service.credit_modality.CreditModalityService;

@Service
public class CreditEligibilityServiceImpl implements CreditEligibilityService {

  private ClientService clientService;
  private AutomotiveCreditService automotiveCreditService;
  private CreditModalityService creditModalityService;

  public CreditEligibilityServiceImpl(ClientService clientService,
      AutomotiveCreditService automotiveCreditService,
      CreditModalityService creditModalityService) {
    this.clientService = clientService;
    this.automotiveCreditService = automotiveCreditService;
    this.creditModalityService = creditModalityService;
  }

  @Override
  public List<NeurotechClient> findEligibleClientsForCarCredit(int minAge, int maxAge, CreditType creditType,
      VehicleModel vehicleModel) {
    List<CreditModality> creditModalities = this.creditModalityService
        .filter(new FilterCreditModality(creditType, null, null, minAge, maxAge));

    if (creditModalities.isEmpty()) {
      throw new CreditModalityNotFoundException(creditType.name());
    }

    if (creditModalities.size() > 1) {
      throw new ConflitException("More than one credit modality found for credit type " + creditType.name(),
          "CREDIT_MODALITY_DUPLICATED");
    }

    CreditModality creditModality = creditModalities.get(0);
    List<NeurotechClient> clients = this.clientService.filter(minAge, maxAge, creditModality.getMinIncome(),
        creditModality.getMaxIncome());

    AutomotiveCredit automotiveCredit = this.automotiveCreditService.getByModel(vehicleModel);

    return clients.stream()
        .filter(c -> (automotiveCredit.getMinIncome() == null || automotiveCredit.getMinIncome() <= c.getIncome())
            && (automotiveCredit.getMaxIncome() == null || c.getIncome() <= automotiveCredit.getMaxIncome()))
        .filter(c -> (automotiveCredit.getMinAge() == null || automotiveCredit.getMinAge() <= c.getAge())
            && (automotiveCredit.getMaxAge() == null || c.getAge() <= automotiveCredit.getMaxAge()))
        .toList();
  }

  @Override
  public boolean checkEligibility(long clientId, VehicleModel vehicleModel) {
    NeurotechClient client = this.clientService.getByIdOrThrow(clientId);
    AutomotiveCredit automotiveCredit = this.automotiveCreditService.getByModel(vehicleModel);

    return (automotiveCredit.getMinIncome() == null || automotiveCredit.getMinIncome() <= client.getIncome())
        && (automotiveCredit.getMaxIncome() == null || client.getIncome() <= automotiveCredit.getMaxIncome())
        && (automotiveCredit.getMinAge() == null || automotiveCredit.getMinAge() <= client.getAge())
        && (automotiveCredit.getMaxAge() == null || client.getAge() <= automotiveCredit.getMaxAge());
  }

}
