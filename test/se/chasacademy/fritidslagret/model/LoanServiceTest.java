package se.chasacademy.fritidslagret.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.Exceptions.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.Exceptions.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserInactiveException;
import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Test
    public void verifyLoanCreation() {
        // Arrange
        String equipmentName = "Hjälm";
        int maxLoanDays = 3;
        EquipmentTypes equipmentType = EquipmentTypes.SKISET;

        // Act
        Member member = new Member("Nisse", MemberStatus.ACTIVE);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), equipmentName, true, maxLoanDays, equipmentType);

        int equipmentId = EquipmentService.getEquipmentByNameToId(equipmentName);
        LoanService.startLoan(equipmentId, member);

        // Assert
        assertEquals(equipmentId, EquipmentService.getUniqueEquipmentId() - 1);
        assertEquals(false, EquipmentService.getEquipmentById(equipmentId).isAvailable());

    }

    @Test
    public void loanRulesAndPolicysCheckedBeforeLoanCreation() {
        // Arrange
        Member member = mock(Member.class);
        Member member1 = mock(Member.class);
        Member memberLastAvailableLoan = mock(Member.class);
        Member memberTooManyLoans = mock(Member.class);
        Member memberInactive = mock(Member.class);
        Member memberRevoked = mock(Member.class);

        int startTestUniqueLoanId = LoanService.getUniqueLoanId();

        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);

        // Act
        when(memberLastAvailableLoan.getActiveLoans()).thenReturn(2);
        when(memberTooManyLoans.getActiveLoans()).thenReturn(3);
        when(member.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberLastAvailableLoan.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberInactive.getMemberStatus()).thenReturn(MemberStatus.INACTIVE);
        when(memberRevoked.getMemberStatus()).thenReturn(MemberStatus.REVOKED);

        // Valid Loans
        LoanService.startLoan(0, member);
        LoanService.startLoan(3, memberLastAvailableLoan);
        int countValidLoansAbove = 2;
        // Invalid Loans
        Executable equipmentNotAvailableAct = () -> LoanService.startLoan(0, member1);
        Executable tooManyLoansAct = () -> LoanService.startLoan(1 , memberTooManyLoans);
        Executable notActiveAct = () -> LoanService.startLoan(2, memberInactive);
        Executable areRevokedAct = () -> LoanService.startLoan(2, memberRevoked);

        // Assert
        assertEquals(LoanService.getUniqueLoanId(), startTestUniqueLoanId + countValidLoansAbove);
        assertThrows(EquipmentNotAvailableException.class, equipmentNotAvailableAct);
        assertThrows(LoanMaximumLimitException.class, tooManyLoansAct);
        assertThrows(UserInactiveException.class, notActiveAct);
        assertThrows(UserRevokedException.class, areRevokedAct);

    }

    public void testReturnDateExpired() {
        // Arrange
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 50, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 90, EquipmentTypes.SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Sko", true, 365, EquipmentTypes.SKISET);

        //LoanService.getExpiredLoans();


        // Act

        // Assert

    }
}
