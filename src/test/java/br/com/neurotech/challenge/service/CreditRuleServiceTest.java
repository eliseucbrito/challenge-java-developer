package br.com.neurotech.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.neurotech.challenge.dto.request.CreateAutomotiveCreditRequest;
import br.com.neurotech.challenge.dto.request.UpdateAutomotiveCreditRequest;
import br.com.neurotech.challenge.entity.AutomotiveCredit.AutomotiveCredit;
import br.com.neurotech.challenge.entity.AutomotiveCredit.VehicleModel;
import br.com.neurotech.challenge.exception.credit.AutomotiveCreditNotFoundException;
import br.com.neurotech.challenge.repository.AutomotiveCreditRepository;
import br.com.neurotech.challenge.service.automotive_credit.AutomotiveCreditServiceImpl;
import br.com.neurotech.challenge.service.client.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
class CreditRuleServiceTest {

  @Mock
  private ClientServiceImpl clientService;
  @Mock
  private AutomotiveCreditRepository automotiveCreditRepository;

  @InjectMocks
  private AutomotiveCreditServiceImpl service;

  private AutomotiveCredit sampleRule;
  private CreateAutomotiveCreditRequest createDto;
  private UpdateAutomotiveCreditRequest updateDto;

  @BeforeEach
  void init() {
    sampleRule = new AutomotiveCredit(null, VehicleModel.SUV, 3000.0, 10000.0, 25, 65);

    createDto = new CreateAutomotiveCreditRequest(
        sampleRule.getVehicleModel(),
        sampleRule.getMinIncome(),
        sampleRule.getMaxIncome(),
        sampleRule.getMinAge(),
        sampleRule.getMaxAge());

    updateDto = new UpdateAutomotiveCreditRequest(
        VehicleModel.HATCH, // trocando modelo
        4000.0,
        12000.0,
        30,
        70);
  }

  @Test
  void save_shouldDelegateToRepository() {
    when(automotiveCreditRepository.save(sampleRule)).thenReturn(sampleRule);

    AutomotiveCredit result = service.save(sampleRule);

    assertSame(sampleRule, result);
    verify(automotiveCreditRepository).save(sampleRule);
  }

  @Test
  void create_shouldConvertDtoAndSave() {
    ArgumentCaptor<AutomotiveCredit> captor = ArgumentCaptor.forClass(AutomotiveCredit.class);
    when(automotiveCreditRepository.save(any())).thenReturn(sampleRule);

    AutomotiveCredit result = service.create(createDto);

    verify(automotiveCreditRepository).save(captor.capture());
    AutomotiveCredit toSave = captor.getValue();

    assertNull(toSave.getId());
    assertEquals(sampleRule.getVehicleModel(), toSave.getVehicleModel());
    assertEquals(sampleRule.getMinIncome(), toSave.getMinIncome());
    assertEquals(sampleRule.getMaxAge(), toSave.getMaxAge());
    assertEquals(result, sampleRule);
  }

  @Test
  void update_existingId_shouldApplyUpdatesAndSave() {
    long id = 42L;
    AutomotiveCredit existing = new AutomotiveCredit(id, VehicleModel.SUV, 2000.0, 8000.0, 20, 60);
    when(automotiveCreditRepository.findById(id)).thenReturn(Optional.of(existing));
    when(automotiveCreditRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    AutomotiveCredit updated = service.update(id, updateDto);

    assertEquals(id, updated.getId());
    assertEquals(VehicleModel.HATCH, updated.getVehicleModel());
    assertEquals(4000.0, updated.getMinIncome());
    assertEquals(12000.0, updated.getMaxIncome());

    verify(automotiveCreditRepository).findById(id);
    verify(automotiveCreditRepository).save(updated);
  }

  @Test
  void update_nonexistentId_shouldThrow() {
    long missingId = 99L;
    when(automotiveCreditRepository.findById(missingId)).thenReturn(Optional.empty());

    assertThrows(AutomotiveCreditNotFoundException.class, () -> service.update(missingId, updateDto));

    verify(automotiveCreditRepository).findById(missingId);
    verify(automotiveCreditRepository, never()).save(any());
  }

}
