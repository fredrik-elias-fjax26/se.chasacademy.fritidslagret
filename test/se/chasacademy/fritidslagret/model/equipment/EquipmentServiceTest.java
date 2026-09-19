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
            Equipment equipment = equipmentService.addEquipment(equipmentName, available, maxLoanDays, equipmentType);

            // Assert
            assertEquals(equipmentName, equipment.getEquipmentName());
            assertEquals(available, equipment.isAvailable());
            assertEquals(maxLoanDays, equipment.getMaxLoanDays());
            assertEquals(equipmentType.getDisplayName(), equipment.getType());

        }
}
