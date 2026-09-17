package se.chasacademy.fritidslagret.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserInactiveException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Test
    public void verifyLoanCreation() {
        // Arrange
        EquipmentService equipmentService = new EquipmentService();
        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService(memberService, equipmentService, Clock.systemUTC());
        String equipmentName = "Hjälm";
        int maxLoanDays = 3;
        EquipmentTypes equipmentType = EquipmentTypes.SKISET;

        // Act
        Member member = new Member(1,"Nisse", MemberStatus.ACTIVE);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), equipmentName, true, maxLoanDays, equipmentType);

        int equipmentId = equipmentService.getEquipmentByNameToId(equipmentName);
        loanService.startLoan(equipmentId, member);

        // Assert
        assertEquals(equipmentId, equipmentService.getUniqueEquipmentId() - 1);
        assertEquals(false, equipmentService.getEquipmentById(equipmentId).isAvailable());

    }

    @Test
    public void loanRulesAndPolicysCheckedBeforeLoanCreation() {
        // Arrange
        EquipmentService equipmentService = new EquipmentService();
        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService(memberService, equipmentService, Clock.systemUTC());
        Member member = mock(Member.class);
        Member member1 = mock(Member.class);
        Member memberLastAvailableLoan = mock(Member.class);
        Member memberTooManyLoans = mock(Member.class);
        Member memberInactive = mock(Member.class);
        Member memberRevoked = mock(Member.class);

        int startTestUniqueLoanId = loanService.getUniqueLoanId();

        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);

        // Act
        when(memberLastAvailableLoan.getActiveLoans()).thenReturn(2);
        when(memberTooManyLoans.getActiveLoans()).thenReturn(3);
        when(member.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberLastAvailableLoan.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberInactive.getMemberStatus()).thenReturn(MemberStatus.INACTIVE);
        when(memberRevoked.getMemberStatus()).thenReturn(MemberStatus.REVOKED);

        // Valid Loans
        loanService.startLoan(0, member);
        loanService.startLoan(3, memberLastAvailableLoan);
        int countValidLoansAbove = 2;
        // Invalid Loans
        Executable equipmentNotAvailableAct = () -> loanService.startLoan(0, member1);
        Executable tooManyLoansAct = () -> loanService.startLoan(1 , memberTooManyLoans);
        Executable notActiveAct = () -> loanService.startLoan(2, memberInactive);
        Executable areRevokedAct = () -> loanService.startLoan(2, memberRevoked);

        // Assert
        assertEquals(loanService.getUniqueLoanId(), startTestUniqueLoanId + countValidLoansAbove);
        assertThrows(EquipmentNotAvailableException.class, equipmentNotAvailableAct);
        assertThrows(LoanMaximumLimitException.class, tooManyLoansAct);
        assertThrows(UserInactiveException.class, notActiveAct);
        assertThrows(UserRevokedException.class, areRevokedAct);

    }

    public void testReturnDateExpired() {
        EquipmentService equipmentService = new EquipmentService();
        // Arrange
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 50, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 90, EquipmentTypes.SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Sko", true, 365, EquipmentTypes.SKISET);

        //LoanService.getExpiredLoans();


        // Act

        // Assert

    }
}
