package br.com.neurotech.challenge.service.ClientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repositories.ClientRepository;


@Service
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String save(NeurotechClient client) {
        return null;
    }

    @Override
    public NeurotechClient get(Long id) {
        return null;
    }

    @Override
    public List<VehicleModel> getAvailableCarsByClientId(Long id) {
        return List.of();
    }
}