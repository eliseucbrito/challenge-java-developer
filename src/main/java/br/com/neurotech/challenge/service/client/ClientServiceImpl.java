package br.com.neurotech.challenge.service.client;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.client.ClientNotFoundException;
import br.com.neurotech.challenge.repository.ClientRepository;
import jakarta.validation.Valid;

@Service
public class ClientServiceImpl implements ClientService {

  private ClientRepository clientRepository;

  @Autowired
  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Optional<NeurotechClient> getById(long id) {
    return this.clientRepository.findById(id);
  }

  @Override
  public NeurotechClient getByIdOrThrow(long id) throws ClientNotFoundException {
    return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(Long.toString(id)));
  }

  @Override
  public NeurotechClient save(@Valid NeurotechClient client) {
    return this.clientRepository.save(client);
  }

  @Override
  public NeurotechClient create(CreateClientRequest createClientRequest) {
    NeurotechClient client = createClientRequest.toEntity();

    return this.clientRepository.save(client);
  }

  @Override
  public List<NeurotechClient> filter(Integer minAge, Integer maxAge, Double minIncome, Double maxIncome) {
    LocalDate now = LocalDate.now();
    return this.clientRepository.filter(now.minusYears(maxAge), now.minusYears(minAge), minIncome, maxIncome);
  }

  @Override
  public void delete(long id) {
    this.getByIdOrThrow(id);
    this.clientRepository.deleteById(id);
  }
}