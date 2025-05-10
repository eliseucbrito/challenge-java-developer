package br.com.neurotech.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.neurotech.challenge.entity.NeurotechClient;

@Repository
public interface ClientRepository extends JpaRepository<NeurotechClient, Long> {
}
