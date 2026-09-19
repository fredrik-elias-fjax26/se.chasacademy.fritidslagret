package se.chasacademy.fritidslagret.model.equipment;

import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.service.EquipmentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentServiceTest {
        @Test
        public void verifyEquipmentCreation() {
            EquipmentService equipmentService = new EquipmentService(EquipmentRepository.getInstance());
            // Arrange
            String equipmentName = "Hjälm";
            boolean available = true;
            int maxLoanDays = 3;
            EquipmentTypes equipmentType = EquipmentTypes.SKISET;

            // Act
            int id = equipmentService.addEquipment(equipmentName, available, maxLoanDays, equipmentType);

            // Assert
            assertEquals(equipmentName, equipmentService.getEquipmentById(id).getEquipmentName());
            assertEquals(available, equipmentService.getEquipmentById(id).isAvailable());
            assertEquals(maxLoanDays, equipmentService.getEquipmentById(id).getMaxLoanDays());
            assertEquals(equipmentType.getDisplayName(), equipmentService.getEquipmentById(id).getType());

        }
}
