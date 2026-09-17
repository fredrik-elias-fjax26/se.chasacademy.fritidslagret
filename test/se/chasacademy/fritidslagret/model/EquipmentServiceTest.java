package se.chasacademy.fritidslagret.model;

import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.service.EquipmentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentServiceTest {
        @Test
        public void verifyEquipmentCreation() {
            EquipmentService equipmentService = new EquipmentService();
            // Arrange
            String equipmentName = "Hjälm";
            boolean available = true;
            int maxLoanDays = 3;
            EquipmentTypes equipmentType = EquipmentTypes.SKISET;

            Member member = new Member(1,"Nisse", MemberStatus.ACTIVE);

            // Act
            int id = equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), equipmentName, available, maxLoanDays, equipmentType);

            // Assert
            assertEquals(equipmentService.getEquipmentById(id).getEquipmentName(), equipmentName);
            assertEquals(equipmentService.getEquipmentById(id).isAvailable(), available);
            assertEquals(equipmentService.getEquipmentById(id).getMaxLoanDays(), maxLoanDays);
            assertEquals(equipmentService.getEquipmentById(id).getType(), equipmentType.getDisplayName());

        }
}
