package br.com.neurotech.challenge.factories;

import br.com.neurotech.challenge.entity.CreditRule;
import br.com.neurotech.challenge.entity.VehicleModel;

public class TestCreditRuleFactory {
  public static CreditRule createValid() {
    return new CreditRule(1L, VehicleModel.HATCH, 1000.0, 5000.0, 18, 65);
  }

  public static CreditRule createInvalid() {
    return new CreditRule(null, null, -1000.0, -5000.0, -18, -65);
  }

}
