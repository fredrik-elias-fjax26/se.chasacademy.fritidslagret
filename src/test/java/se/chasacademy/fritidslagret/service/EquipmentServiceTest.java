package se.chasacademy.fritidslagret.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.CantDeleteEquipmentWithActiveLoanException;
import se.chasacademy.fritidslagret.exception.EquipmentIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.equipment.Tent;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipmentServiceTest {
    @Disabled // Only run when there is no save method in EquipmentRepository
    @Test
    public void verifyEquipmentCreationDontRunWithSavedData() {
        // Arrange
        EquipmentService equipmentService = new EquipmentService(EquipmentRepository.getInstance());

        String equipmentName = "Hjälm";
        boolean available = true;
        int maxLoanDays = 3;
        EquipmentTypes equipmentType = EquipmentTypes.SKISET;

        // Act
        Equipment equipment = equipmentService.addEquipment(equipmentName, available, maxLoanDays, equipmentType);

        // Assert
        assertEquals(equipmentName, equipment.getEquipmentName());
        assertEquals(available, equipment.isAvailable());
        assertEquals(maxLoanDays, equipment.getMaxLoanDays());
        assertEquals(equipmentType.getDisplayName(), equipment.getType());

    }

    @Test
    public void shouldReturnListOfEquipmentBySearchTerm() {
        // Arrange
        EquipmentRepository equipmentRepository = mock(EquipmentRepository.class);
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);

        Equipment equipment1 = new SkiSet("fakeSkies", true, 3);
        Equipment equipment2 = new Tent("fakeTent", false, 4);

        Map<Integer, Equipment> fakeEquipmentList = Map.of(equipment1.getEquipmentId(), equipment1, equipment2.getEquipmentId(), equipment2);

        when(equipmentRepository.getEquipmentDatabase()).thenReturn(fakeEquipmentList);

        // Act
        List<Equipment> searchedEquipmentList = equipmentService.searchForEquipmentByName("fake");


        // Assert
        assertEquals(List.copyOf(fakeEquipmentList.values()), searchedEquipmentList);

    }

    @Test
    public void shouldRemoveEquipmentFromRepository() {
        // Arrange
        EquipmentRepository equipmentRepository = mock(EquipmentRepository.class);
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);

        Equipment equipment1 = new SkiSet("fakeSkies", true, 3);

        Map<Integer, Equipment> fakeEquipmentMapRepo = new HashMap<>(Map.of(equipment1.getEquipmentId(), equipment1));

        boolean fakeEquipmentExists;
        fakeEquipmentExists = fakeEquipmentMapRepo.containsValue(equipment1);

        doAnswer(_ -> {
            fakeEquipmentMapRepo.remove(equipment1.getEquipmentId());
            return null;
        }).when(equipmentRepository).removeEquipment(equipment1);

        // Act
        equipmentService.deleteEquipment(equipment1);

        // Assert
        assertTrue(fakeEquipmentExists);
        assertTrue(fakeEquipmentMapRepo.isEmpty());
    }

    @Test
    public void shouldThrowEquipmentIsNullExceptionWhenEquipmentIsNull() {
        // Arrange
        EquipmentRepository equipmentRepository = mock(EquipmentRepository.class);
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        Equipment equipment1 = null;

        // Act
        Executable throwAct = () -> equipmentService.deleteEquipment(equipment1);

        // Assert
        assertThrows(EquipmentIsNullException.class,  throwAct);

    }

    @Test
    public void shouldThrowCantDeleteEquipmentWithActiveLoanException() {
        // Arrange
        EquipmentRepository equipmentRepository = mock(EquipmentRepository.class);

        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        Equipment equipment1 = new SkiSet("fakeSkies", false, 3);
        Map<Integer, Equipment>  fakeEquipmentMapRepo = new HashMap<>(Map.of(equipment1.getEquipmentId(), equipment1));

        when(equipmentRepository.getEquipmentDatabase()).thenReturn(fakeEquipmentMapRepo);

        // Act
        Executable throwAct = () -> equipmentService.deleteEquipment(equipment1);

        // Assert
        assertThrows(CantDeleteEquipmentWithActiveLoanException.class, throwAct);

    }




}
