package br.com.neurotech.challenge.service.ClientService;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

@Service
public interface IClientService {

	/**
	 * Saves a new client
	 *
	 * @param client
	 * @return client id
	 */
	String save(NeurotechClient client);

	/**
	 * Look for a client
	 *
	 * @param id
	 * @return client or null
	 */
	NeurotechClient get(Long id);


	/**
	 * Get cars models with available credit for a client
	 *
	 * @param id
	 * @return list of available cars models
	 */
	List<VehicleModel> getAvailableCarsByClientId(Long id);
}
