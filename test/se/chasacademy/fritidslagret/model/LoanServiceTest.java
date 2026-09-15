package se.chasacademy.fritidslagret.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanServiceTest {

    @Test
    public void verifyLoanCreation() {
        // Arrange
        String equipmentName = "Hjälm";
        boolean available = true;
        int maxLoanDays = 3;
        EquipmentTypes equipmentType = EquipmentTypes.SKISET;

        // Act
        Member member = new Member("Nisse", MemberStatus.ACTIVE);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), equipmentName, available, maxLoanDays, equipmentType);

        int equipmentId = EquipmentService.getEquipmentByNameToId(equipmentName);
        LoanService.startLoan(equipmentId, member);

        // Assert
        assertEquals(EquipmentService.getUniqueEquipmentId() - 1, equipmentId);
        assertEquals(EquipmentService.getEquipmentById(equipmentId).isAvailable(), available);

    }

}
