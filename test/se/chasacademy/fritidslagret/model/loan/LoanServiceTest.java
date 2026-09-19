package se.chasacademy.fritidslagret.model.loan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserInactiveException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Test
    public void verifyLoanCreation() {
        // Arrange
        EquipmentService equipmentService = new EquipmentService(EquipmentRepository.getInstance());
        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService(memberService, equipmentService, Clock.systemUTC());
        String equipmentName = "Hjälm";
        int maxLoanDays = 3;
        EquipmentTypes equipmentType = EquipmentTypes.SKISET;
        Member member = new Member(1,"Nisse", MemberStatus.ACTIVE);
        int equipmentId = equipmentService.addEquipment(equipmentName, true, maxLoanDays, equipmentType);

        // Act
        loanService.startLoan(equipmentId, member);

        // Assert
        assertFalse(equipmentService.getEquipmentById(equipmentId).isAvailable());

    }

    @Test
    public void loanRulesAndPolicysCheckedBeforeLoanCreation() {
        // Arrange
        EquipmentService equipmentService = new EquipmentService(EquipmentRepository.getInstance());
        MemberService memberService = new MemberService();
        LoanService loanService = new LoanService(memberService, equipmentService, Clock.systemUTC());
        Member member = mock(Member.class);
        Member member1 = mock(Member.class);
        Member memberLastAvailableLoan = mock(Member.class);
        Member memberTooManyLoans = mock(Member.class);
        Member memberInactive = mock(Member.class);
        Member memberRevoked = mock(Member.class);

        int startTestUniqueLoanId = loanService.getUniqueLoanId();
        int startTestUniqueEquipmentId = equipmentService.getUniqueEquipmentId();

        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);

        // Act
        when(memberLastAvailableLoan.getActiveLoans()).thenReturn(2);
        when(memberTooManyLoans.getActiveLoans()).thenReturn(3);
        when(member.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberLastAvailableLoan.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberInactive.getMemberStatus()).thenReturn(MemberStatus.INACTIVE);
        when(memberRevoked.getMemberStatus()).thenReturn(MemberStatus.REVOKED);

        // Valid Loans
        loanService.startLoan(startTestUniqueEquipmentId, member);
        loanService.startLoan(startTestUniqueEquipmentId + 3, memberLastAvailableLoan);
        int countValidLoansAbove = 2;
        // Invalid Loans
        Executable equipmentNotAvailableAct = () -> loanService.startLoan(startTestUniqueEquipmentId, member1);
        Executable tooManyLoansAct = () -> loanService.startLoan(startTestUniqueEquipmentId + 1 , memberTooManyLoans);
        Executable notActiveAct = () -> loanService.startLoan(startTestUniqueEquipmentId + 2, memberInactive);
        Executable areRevokedAct = () -> loanService.startLoan(startTestUniqueEquipmentId + 2, memberRevoked);

        // Assert
        assertEquals(startTestUniqueLoanId + countValidLoansAbove, loanService.getUniqueLoanId());
        assertThrows(EquipmentNotAvailableException.class, equipmentNotAvailableAct);
        assertThrows(LoanMaximumLimitException.class, tooManyLoansAct);
        assertThrows(UserInactiveException.class, notActiveAct);
        assertThrows(UserRevokedException.class, areRevokedAct);

    }

    @Test
    public void checkLoanServiceReportsLoanExpiredWhenReturnDatePassesTest(){
        //Arrange
        int maxLoanDays = 3;
        int fakeId = 1;
        String fakeName = "fakeName";

        Member member = new Member(fakeId,fakeName, MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet(fakeName, true, maxLoanDays);
        LocalDate acquiredDate = LocalDate.now();

        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        Clock clock = mock(Clock.class);

        Loan loan1 = new Loan(fakeId, member, equipment, acquiredDate, acquiredDate.plusDays(maxLoanDays), true);

        //Act
        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays + 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(memberService, equipmentService, clock);

        //Assert
        assertTrue(loanService.isDelayed(loan1));
        verify(clock).instant();
    }

    @Test
    public void checkLoanServiceNotReportsLoanExpiredWhenOnSameDateAsReturnDateTest(){
        int maxLoanDays = 3;
        int fakeId = 1;
        LocalDate acquiredDate = LocalDate.now();
        String fakeName = "fakeName";

        Member member = new Member(fakeId,fakeName, MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet(fakeName, true, maxLoanDays);
        MemberService memberService = mock(MemberService.class);

        EquipmentService equipmentService = mock(EquipmentService.class);
        Clock clock = mock(Clock.class);

        Loan loan1 = new Loan(fakeId, member, equipment, acquiredDate, acquiredDate.plusDays(maxLoanDays), true);

        //Act
        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(memberService, equipmentService, clock);

        //Assert
        assertFalse(loanService.isDelayed(loan1));
        verify(clock).instant();
    }


}
