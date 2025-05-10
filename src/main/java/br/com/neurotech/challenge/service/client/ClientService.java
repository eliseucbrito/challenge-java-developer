package br.com.neurotech.challenge.service.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exceptions.client.ClientNotFoundException;
import br.com.neurotech.challenge.repositories.ClientRepository;
import jakarta.validation.Valid;

@Service
public class ClientService implements IClientService {

  private ClientRepository clientRepository;

  @Autowired
  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Optional<NeurotechClient> getById(long id) {
    return this.clientRepository.findById(id);
  }

  public NeurotechClient getByIdOrThrow(long id) {
    return this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(Long.toString(id)));
  }

  @Override
  public NeurotechClient save(@Valid NeurotechClient client) {
    return this.clientRepository.save(client);
  }
}