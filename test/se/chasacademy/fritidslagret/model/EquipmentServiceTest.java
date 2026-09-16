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
            // Arrange
            String equipmentName = "Hjälm";
            boolean available = true;
            int maxLoanDays = 3;
            EquipmentTypes equipmentType = EquipmentTypes.SKISET;

            Member member = new Member(1,"Nisse", MemberStatus.ACTIVE);

            // Act
            int id = EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), equipmentName, available, maxLoanDays, equipmentType);

            // Assert
            assertEquals(EquipmentService.getEquipmentById(id).getEquipmentName(), equipmentName);
            assertEquals(EquipmentService.getEquipmentById(id).isAvailable(), available);
            assertEquals(EquipmentService.getEquipmentById(id).getMaxLoanDays(), maxLoanDays);
            assertEquals(EquipmentService.getEquipmentById(id).getType(), equipmentType.getDisplayName());

        }
}
