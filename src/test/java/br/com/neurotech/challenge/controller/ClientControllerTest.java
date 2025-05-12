package br.com.neurotech.challenge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.neurotech.challenge.dto.request.CreateClientRequest;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.exception.client.ClientNotFoundException;
import br.com.neurotech.challenge.service.client.ClientService;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  private ObjectMapper objectMapper;

  private NeurotechClient testClient;
  private CreateClientRequest createClientRequest;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    testClient = new NeurotechClient(1L, "Test User", birthDate, 5000.0);
    createClientRequest = new CreateClientRequest("Test User", birthDate, 5000.0);
  }

  @Test
  @DisplayName("Should create a client and return 201 Created")
  void testCreateClient() throws Exception {
    when(clientService.create(any(CreateClientRequest.class))).thenReturn(testClient);

    mockMvc.perform(post("/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(createClientRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost:8080/clients/1"));

    verify(clientService, times(1)).create(any(CreateClientRequest.class));
  }

  @Test
  @DisplayName("Should return 400 Bad Request when creating client with invalid data")
  void testCreateClientWithInvalidData() throws Exception {
    // Criar uma solicitação inválida (nome em branco)
    CreateClientRequest invalidRequest = new CreateClientRequest("", LocalDate.of(1990, 1, 1), 5000.0);

    mockMvc.perform(post("/clients")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());

    verify(clientService, never()).create(any(CreateClientRequest.class));
  }

  @Test
  @DisplayName("Should return a client when request by ID")
  void testGetClientById() throws Exception {
    when(clientService.getByIdOrThrow(anyLong())).thenReturn(testClient);

    mockMvc.perform(get("/clients/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Test User"))
        .andExpect(jsonPath("$.income").value(5000.0));

    verify(clientService, times(1)).getByIdOrThrow(1L);
  }

  @Test
  @DisplayName("Should return 404 Not Found when client does not exist")
  void testGetClientByIdNotFound() throws Exception {
    when(clientService.getByIdOrThrow(anyLong())).thenThrow(new ClientNotFoundException("Client not found"));

    mockMvc.perform(get("/clients/999"))
        .andExpect(status().isNotFound());

    verify(clientService, times(1)).getByIdOrThrow(999L);
  }

  @Test
  @DisplayName("Should delete a client and return 204 No Content")
  void testDeleteClient() throws Exception {
    doNothing().when(clientService).delete(anyLong());

    mockMvc.perform(delete("/clients/1"))
        .andExpect(status().isNoContent());

    verify(clientService, times(1)).delete(1L);
  }

  @Test
  @DisplayName("Should return 404 Not Found when deleting non-existent client")
  void testDeleteClientNotFound() throws Exception {
    doThrow(new ClientNotFoundException("Client not found")).when(clientService).delete(anyLong());

    mockMvc.perform(delete("/clients/999"))
        .andExpect(status().isNotFound());

    verify(clientService, times(1)).delete(999L);
  }
}
