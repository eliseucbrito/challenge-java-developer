
package br.com.neurotech.challenge.factories;

import java.time.LocalDate;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;

public class TestCreateClientRequestFactory {
  public static CreateClientRequest valid() {
    return new CreateClientRequest("John Doe", LocalDate.now().minusYears(20), 5000.0);
  }

  public static CreateClientRequest invalid() {
    return new CreateClientRequest("", LocalDate.now().minusYears(10), -5000.0);
  }

  public static CreateClientRequest nullValues() {
    return new CreateClientRequest(null, null, null);
  }
}
