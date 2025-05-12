package br.com.neurotech.challenge.factories;

import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;

public class TestCreditRuleFactory {
  public static AutomotiveCredit createValid() {
    return new AutomotiveCredit(1L, VehicleModel.HATCH, 1000.0, 5000.0, 18, 65);
  }

  public static AutomotiveCredit createInvalid() {
    return new AutomotiveCredit(null, null, -1000.0, -5000.0, -18, -65);
  }

}
