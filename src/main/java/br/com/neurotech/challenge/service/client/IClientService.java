package br.com.neurotech.challenge.service.client;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exceptions.client.ClientNotFoundException;

@Service
public interface IClientService {

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
	 * Method to get a client by ID or throw an exception
	 *
	 * @param id must not be null
	 * @return the client with the given id
	 * @throws ClientNotFoundException if the client is not found
	 */
	NeurotechClient getByIdOrThrow(long id);
}
