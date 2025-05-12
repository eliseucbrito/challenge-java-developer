package br.com.neurotech.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.client.ClientNotFoundException;
import br.com.neurotech.challenge.factories.TestClientFactory;
import br.com.neurotech.challenge.repository.ClientRepository;
import br.com.neurotech.challenge.service.client.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientServiceImpl clientService;

  private NeurotechClient savedClient;
  private NeurotechClient unsavedClient;
  private CreateClientRequest createClientRequest;

  private final long CLIENT_ID = 1L;

  @BeforeEach
  void setUp() {
    unsavedClient = TestClientFactory.valid();
    unsavedClient.setId(null);

    savedClient = TestClientFactory.valid();
    savedClient.setId(CLIENT_ID);

    createClientRequest = new CreateClientRequest(unsavedClient.getName(), unsavedClient.getBirthDate(),
        unsavedClient.getIncome());
  }

  @Test
  void shouldReturnClientWhenGettingExistingClientById() {
    when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(savedClient));

    Optional<NeurotechClient> result = clientService.getById(CLIENT_ID);

    assertTrue(result.isPresent());
    assertEquals(savedClient, result.get());
    verify(clientRepository).findById(CLIENT_ID);
  }

  @Test
  void shouldReturnEmptyOptionalWhenGettingNonExistingClientById() {
    when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

    Optional<NeurotechClient> result = clientService.getById(CLIENT_ID);

    assertFalse(result.isPresent());
    verify(clientRepository).findById(CLIENT_ID);
  }

  @Test
  void shouldReturnClientWhenGettingExistingClientByIdOrThrow() {
    when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(savedClient));

    NeurotechClient result = clientService.getByIdOrThrow(CLIENT_ID);

    assertEquals(savedClient, result);
    verify(clientRepository).findById(CLIENT_ID);
  }

  @Test
  void shouldThrowExceptionWhenGettingNonExistingClientByIdOrThrow() {
    when(clientRepository.findById(CLIENT_ID)).thenReturn(Optional.empty());

    ClientNotFoundException exception = assertThrows(
        ClientNotFoundException.class,
        () -> clientService.getByIdOrThrow(CLIENT_ID));

    assertEquals("Client not found with identifier: 1", exception.getMessage());
    verify(clientRepository).findById(CLIENT_ID);
  }

  @Test
  void shouldReturnSavedClientWhenSaving() {
    when(clientRepository.save(unsavedClient)).thenReturn(savedClient);

    NeurotechClient result = clientService.save(unsavedClient);

    assertEquals(savedClient, result);
    verify(clientRepository).save(unsavedClient);
  }

  @Test
  void shouldReturnSavedClientWithCorrectId() {
    when(clientRepository.save(unsavedClient)).thenReturn(savedClient);

    NeurotechClient result = clientService.save(unsavedClient);

    assertEquals(CLIENT_ID, result.getId());
    verify(clientRepository).save(unsavedClient);
  }

  @Test
  void shouldReturnCreatedClientWhenCreatingFromValidRequest() {
    when(clientRepository.save(any(NeurotechClient.class))).thenReturn(savedClient);

    NeurotechClient result = clientService.create(createClientRequest);

    assertEquals(savedClient, result);
    verify(clientRepository).save(any(NeurotechClient.class));
  }
}
