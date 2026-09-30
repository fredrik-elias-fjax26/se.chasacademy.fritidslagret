package se.chasacademy.fritidslagret.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.*;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.repository.LoanRepository;
import se.chasacademy.fritidslagret.repository.MemberRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

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
        int startTestUniqueEquipmentId = Equipment.getUniqueEquipmentId();

        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);
        equipmentService.addEquipment("Sko", true, 30, EquipmentTypes.SKISET);

        when(memberLastAvailableLoan.getActiveLoans()).thenReturn(2);
        when(memberTooManyLoans.getActiveLoans()).thenReturn(3);
        when(member.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberLastAvailableLoan.getMemberStatus()).thenReturn(MemberStatus.ACTIVE);
        when(memberInactive.getMemberStatus()).thenReturn(MemberStatus.INACTIVE);
        when(memberRevoked.getMemberStatus()).thenReturn(MemberStatus.REVOKED);

        // Act
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

        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays + 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        //Act
        boolean isDelayed = loanService.isDelayed(loan);

        //Assert
        assertTrue(isDelayed);
        verify(clock).instant();
    }

    @Test
    public void checkLoanServiceNotReportsLoanExpiredWhenOnSameDateAsReturnDateTest(){
        //Arrange
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

        when(clock.instant()).thenReturn(acquiredDate.plusDays(maxLoanDays).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        //Act
        boolean isDelayed = loanService.isDelayed(loan1);

        //Assert
        assertFalse(isDelayed);
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

        when(waitingListService.isMembersInQueueForEquipment(equipment)).thenReturn(true);
        when(waitingListService.getFromWaitingList(equipment)).thenReturn(nextMemberInQueue);
        when(equipmentService.getEquipmentById(equipment.getEquipmentId())).thenReturn(equipment);
        when(clock.instant()).thenReturn(Clock.systemUTC().instant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        // Act
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

        when(waitingListService.isMembersInQueueForEquipment(equipment)).thenReturn(false);

        // Act
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

        when(equipmentService.getEquipmentById(equipmentId)).thenReturn(equipment);
        when(loanRepository.addLoan(loan)).thenReturn(loan);

        // Act
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

    @Test
    public void shouldReturnDelayedLoan() {
        // Arrange
        Clock clock = mock(Clock.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);

        LocalDate fixedReturnDate = LocalDate.of(2021, 1, 10);

        Equipment equipment = new SkiSet("fakeName", true, 3);
        Member member = new Member("fakeName", MemberStatus.ACTIVE);

        LoanService loanService = new LoanService(loanRepository,memberService,equipmentService, waitingListService, clock);

        Loan loanDelayed = new Loan(member, equipment, fixedReturnDate.minusDays(7), fixedReturnDate, true);

        Map<Integer, Loan> loans = new HashMap<>();

        loans.put(loanDelayed.getLoanId(), loanDelayed);

        when(clock.instant()).thenReturn(fixedReturnDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        when(loanRepository.getLoanDatabase()).thenReturn(loans);

        // Act
        List<Loan> delayedLoansList = new ArrayList<>(loanService.getDelayedLoansSortedByDate());

        Map<Integer, Loan> mapOfDelayedLoans = new HashMap<>();
        for (Loan loan : delayedLoansList) {
            mapOfDelayedLoans.put(loan.getLoanId(), loan);
        }

        // Assert
        assertEquals(loans, mapOfDelayedLoans);
        assertEquals(1, delayedLoansList.size());
    }


    @Test
    public void shouldReturnBothDelayedLoansWhenOnSameDay() {
        // Arrange
        Clock clock = mock(Clock.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);

        LocalDate fixedReturnDate = LocalDate.of(2021, 1, 10);

        Equipment equipment = new SkiSet("fakeName", true, 3);
        Equipment equipment2 = new SkiSet("fakeName", true, 3);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Member member2 = new Member("fakeName", MemberStatus.ACTIVE);

        LoanService loanService = new LoanService(loanRepository,memberService,equipmentService, waitingListService, clock);

        Loan firstLoanDelayed = new Loan(member, equipment, fixedReturnDate.minusDays(7), fixedReturnDate, true);
        Loan secondLoanDelayed = new Loan(member2, equipment2, fixedReturnDate.minusDays(7), fixedReturnDate, true);

        Map<Integer, Loan> loans = new HashMap<>();
        loans.put(firstLoanDelayed.getLoanId(), firstLoanDelayed);
        loans.put(secondLoanDelayed.getLoanId(), secondLoanDelayed);

        when(clock.instant()).thenReturn(fixedReturnDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        when(loanRepository.getLoanDatabase()).thenReturn(loans);

        // Act

        int numberOfLoansInList = loanService.getDelayedLoansSortedByDate().size();

        // Assert
        assertEquals(2, numberOfLoansInList);
    }

    @Test
    public void shouldThrowLoanAlreadyReturnedIfTryingToReturnLoanTwice() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = Clock.systemUTC();

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        when(equipmentService.getEquipmentById(equipment.getEquipmentId())).thenReturn(equipment);

        Loan loan = loanService.startLoan(equipment.getEquipmentId(), member);

        loanService.returnLoan(loan);

        // Act
        Executable dualLoanReturnAct = () -> loanService.returnLoan(loan);

        // Assert
        assertThrows(LoanAlreadyReturned.class, dualLoanReturnAct);
    }

    @Test
    public void shouldReturnCorrectDelayedDaysWhenPassingFromOneMonthToAnother() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = mock(Clock.class);

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        LocalDate fixedReturnDate = LocalDate.of(2021, 1, 31);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);

        Loan loan = new Loan(member, equipment, fixedReturnDate.minusDays(1), fixedReturnDate, true);

        when(clock.instant()).thenReturn(fixedReturnDate.plusDays(5).atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        // Act
        long delayedDays = loanService.getDelayedDays(loan);

        // Assert
        assertEquals(5, delayedDays);
    }

    @Test
    public void shouldIncludeReturnedLoansWhenGettingAllLoans() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = Clock.systemUTC();

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan activeLoan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), true);
        Loan returnedLoan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), false);

        when(loanRepository.getLoanDatabase()).thenReturn(Map.of(
                activeLoan.getLoanId(), activeLoan,
                returnedLoan.getLoanId(), returnedLoan));

        // Act
        List<Loan> allLoans = loanService.getAllLoans();

        // Assert
        assertEquals(2, allLoans.size());
        assertTrue(allLoans.contains(returnedLoan));
    }

    @Test
    public void shouldNotListReturnedLoansAsActiveLoans() {
        // Arrange
        LoanRepository loanRepository = mock(LoanRepository.class);
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        WaitingListService waitingListService = mock(WaitingListService.class);
        Clock clock = Clock.systemUTC();

        LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);

        Member member = new Member("fakeName", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("fakeName", true, 3);
        Loan firstActiveLoan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), true);
        Loan returnedLoan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan secondActiveLoan = new Loan(member, equipment, LocalDate.now(), LocalDate.now().plusDays(3), true);

        when(loanRepository.getLoanDatabase()).thenReturn(Map.of(
                firstActiveLoan.getLoanId(), firstActiveLoan,
                returnedLoan.getLoanId(), returnedLoan,
                secondActiveLoan.getLoanId(), secondActiveLoan));

        // Act
        SortedMap<Integer, Loan> activeLoans = loanService.getAllActiveLoansSortedById();

        // Assert
        assertEquals(List.of(firstActiveLoan, secondActiveLoan), List.copyOf(activeLoans.values()));
    }
}
