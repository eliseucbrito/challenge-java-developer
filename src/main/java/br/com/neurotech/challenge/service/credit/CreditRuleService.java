package br.com.neurotech.challenge.service.credit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.CreateCreditRuleRequest;
import br.com.neurotech.challenge.dto.request.UpdateCreditRuleRequest;
import br.com.neurotech.challenge.entity.CreditRule;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.client.ClientService;
import jakarta.validation.Valid;

@Service
public class CreditRuleService implements ICreditRuleService {

  private ClientService clientService;

  @Autowired
  public CreditRuleService(ClientService clientService) {
    this.clientService = clientService;
  }

  @Override
  public CreditRule save(CreditRule creditRule) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public CreditRule update(long id, UpdateCreditRuleRequest updateFields) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public boolean checkCredit(long clientId, VehicleModel model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'checkCredit'");
  }

  @Override
  public CreditRule create(@Valid CreateCreditRuleRequest creditRuleRequest) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public List<CreditRule> findCreditOptionsByVehicleModel(long clientId, VehicleModel model) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findCreditOptionsByVehicleModel'");
  }

}
