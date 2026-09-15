package se.chasacademy.fritidslagret.model;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void loanRulesCheckedBeforeLoanCreation() {
        // Arrange
        Member member = mock(Member.class);
        Equipment equipment = mock(Equipment.class);
        int equipmentId = 0;
        int oldUniqueLoanId = LoanService.getUniqueLoanId();
        MockedStatic<EquipmentService> mockedStatic = mockStatic(EquipmentService.class);

        // Act
        mockedStatic.when(() -> EquipmentService.getEquipmentById(equipmentId)).thenReturn(equipment);

        when(equipment.isAvailable()).thenReturn(true);
        LoanService.startLoan(equipmentId , member);

        // Assert
        assertEquals(LoanService.getUniqueLoanId(), oldUniqueLoanId + 1);

    }

}
