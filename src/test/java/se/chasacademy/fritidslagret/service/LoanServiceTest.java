package se.chasacademy.fritidslagret.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserInactiveException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.repository.LoanRepository;
import se.chasacademy.fritidslagret.repository.MemberRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    @Test
    public void verifyLoanCreation() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberRepository memberRepository = mock(MemberRepository.class);
        EquipmentRepository equipmentRepository = EquipmentRepository.getInstance();

        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        MemberService memberService = new MemberService(memberRepository);
        WaitingListService waitingListService = mock(WaitingListService.class);

        LoanService loanService = new LoanService(loanRepository ,memberService, equipmentService, waitingListService, Clock.systemUTC());

        String equipmentName = "Hjälm";
        int maxLoanDays = 3;

        EquipmentTypes equipmentType = EquipmentTypes.SKISET;
        Member member = new Member("Nisse", MemberStatus.ACTIVE);
        Equipment equipment = equipmentService.addEquipment(equipmentName, true, maxLoanDays, equipmentType);

        // Act
        Loan loan = loanService.startLoan(equipment.getEquipmentId(), member);

        // Assert
        assertFalse(equipment.isAvailable());
        verify(loanRepository).addLoan(loan);

    }

    @Test
    public void loanRulesAndPolicysCheckedBeforeLoanCreation() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberRepository memberRepository = mock(MemberRepository.class);

        EquipmentService equipmentService = new EquipmentService(EquipmentRepository.getInstance());
        MemberService memberService = new MemberService(memberRepository);
        WaitingListService waitingListService = mock(WaitingListService.class);

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, Clock.systemUTC());

        Member member = mock(Member.class);
        Member member1 = mock(Member.class);
        Member memberLastAvailableLoan = mock(Member.class);
        Member memberTooManyLoans = mock(Member.class);
        Member memberInactive = mock(Member.class);
        Member memberRevoked = mock(Member.class);

        int startTestUniqueLoanId = Loan.getUniqueLoanId();
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
        assertEquals(startTestUniqueLoanId + countValidLoansAbove, Loan.getUniqueLoanId());
        assertThrows(EquipmentNotAvailableException.class, equipmentNotAvailableAct);
        assertThrows(LoanMaximumLimitException.class, tooManyLoansAct);
        assertThrows(UserInactiveException.class, notActiveAct);
        assertThrows(UserRevokedException.class, areRevokedAct);

    }

    @Test
    public void checkLoanServiceReportsLoanExpiredWhenReturnDatePassesTest(){
        //Arrange
        int maxLoanDays = 3;
        String fakeName = "fakeName";

        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = mock(Clock.class);

        Member member = new Member(fakeName, MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet(fakeName, true, maxLoanDays);
        LocalDate acquiredDate = LocalDate.now();

        Loan loan = new Loan(member, equipment, acquiredDate, acquiredDate.plusDays(maxLoanDays), true);

        //Act
        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays + 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        //Assert
        assertTrue(loanService.isDelayed(loan));
        verify(clock).instant();
    }

    @Test
    public void checkLoanServiceNotReportsLoanExpiredWhenOnSameDateAsReturnDateTest(){
        int maxLoanDays = 3;
        LocalDate acquiredDate = LocalDate.now();
        String fakeName = "fakeName";

        LoanRepository loanRepository = mock(LoanRepository.class);

        Member member = new Member(fakeName, MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet(fakeName, true, maxLoanDays);

        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);

        Clock clock = mock(Clock.class);

        Loan loan1 = new Loan(member, equipment, acquiredDate, acquiredDate.plusDays(maxLoanDays), true);

        //Act
        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        //Assert
        assertFalse(loanService.isDelayed(loan1));
        verify(clock).instant();
    }

    @Test
    public void shouldReturnALoanWhenEquipmentHasQueue() {
        // Arrange
        WaitingListService waitingListService = mock(WaitingListService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        MemberService memberService = mock(MemberService.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        Clock clock = mock(Clock.class);

        Member member = new Member("Member", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan loanToReturn = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), true);
        Member nextMemberInQueue = new Member("Next Member", MemberStatus.ACTIVE);
        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        // Act
        when(waitingListService.isMembersInQueueForEquipment(equipment)).thenReturn(true);
        when(waitingListService.getFromWaitingList(equipment)).thenReturn(nextMemberInQueue);
        when(equipmentService.getEquipmentById(equipment.getEquipmentId())).thenReturn(equipment);
        when(clock.instant()).thenReturn(Clock.systemUTC().instant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Loan loanFromQueue = loanService.returnLoan(loanToReturn);
        Member memberFromQueue = loanFromQueue.getMember();

        // Assert
        assertEquals(nextMemberInQueue, memberFromQueue);
    }

    @Test
    public void shouldReturnNullIfQueueIsEmpty() {
        //Arrange
        WaitingListService waitingListService = mock(WaitingListService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        MemberService memberService = mock(MemberService.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        Clock clock = mock(Clock.class);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan loan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), true);

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        // Act
        when(waitingListService.isMembersInQueueForEquipment(equipment)).thenReturn(false);

        Loan nullableAct = loanService.returnLoan(loan);

        // Assert
        assertNull(nullableAct);

    }

    @Test
    public void shouldIncreaseMemberActiveLoans() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = Clock.systemUTC();

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan loan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(1), true);
        int equipmentId = equipment.getEquipmentId();

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        // Act
        when(equipmentService.getEquipmentById(equipmentId)).thenReturn(equipment);
        when(loanRepository.addLoan(loan)).thenReturn(loan);
        loanService.startLoan(equipmentId, member);

        // Assert
        verify(memberService).addToActiveLoans(member);

    }

    @Test
    public void shouldDecreaseMembersActiveLoansWhenLoanIsReturned() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = mock(Clock.class);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan loan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(1), true);

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        // Act

        loanService.returnLoan(loan);

        // Assert
        verify(memberService).removeFromActiveLoans(member);

    }

}
