package br.com.neurotech.challenge.service.client;

import java.util.List;
import java.util.Optional;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.client.ClientNotFoundException;

public interface ClientService {

  /**
   * Method to persist a client
   *
   * @param client the client to be persisted
   * @return the ID of the persisted client
   */
  NeurotechClient save(NeurotechClient client);

  /**
   * Method to get a client by ID
   *
   * @param id must not be null
   * @return the client with the given id or Optional#empty() if none found.
   */
  Optional<NeurotechClient> getById(long id);

  /**
   * Method to create a client
   *
   * @param createClientRequest the client request
   * @return the created client
   */
  NeurotechClient create(CreateClientRequest createClientRequest);

  /**
   * Method to filter clients by criteria
   *
   * @param minAge    the minimum age
   * @param maxAge    the maximum age
   * @param minIncome the minimum income
   * @param maxIncome the maximum income
   * @return list of clients that match the criteria
   */
  List<NeurotechClient> filter(Integer minAge, Integer maxAge, Double minIncome, Double maxIncome);

  /**
   * Method to get a client by ID or throw an exception
   *
   * @param id must not be null
   * @return the client with the given id
   * @throws ClientNotFoundException if the client is not found
   */
  NeurotechClient getByIdOrThrow(long id) throws ClientNotFoundException;

  /**
   * Delete a credit modality.
   *
   * @param id the credit modality ID to delete
   */
  void delete(long id);
}
