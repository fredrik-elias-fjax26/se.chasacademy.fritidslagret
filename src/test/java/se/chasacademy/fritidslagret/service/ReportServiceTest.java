package se.chasacademy.fritidslagret.service;

import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.model.equipment.Bike;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.equipment.Tent;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Test
    public void shouldCountReturnedLoansWhenCountingLoansForEquipment() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member member = new Member("fakeMember", MemberStatus.ACTIVE);
        Equipment bike = new Bike("fakeBike", false, 3);
        Loan returnedLoan = new Loan(member, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan activeLoan = new Loan(member, bike, LocalDate.now(), LocalDate.now().plusDays(3), true);

        when(loanService.getAllLoans()).thenReturn(List.of(returnedLoan, activeLoan));

        // Act
        int loanCount = reportService.getLoanCountForEquipment(bike);

        // Assert
        assertEquals(2, loanCount);
    }

    @Test
    public void shouldReturnEquipmentWithMostLoans() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member member = new Member("fakeMember", MemberStatus.ACTIVE);
        Equipment tent = new Tent("fakeTent", true, 5);
        Equipment bike = new Bike("fakeBike", true, 3);
        Loan tentLoan = new Loan(member, tent, LocalDate.now(), LocalDate.now().plusDays(5), false);
        Loan firstBikeLoan = new Loan(member, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan secondBikeLoan = new Loan(member, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);

        when(equipmentService.getAllEquipmentsSortedById()).thenReturn(new TreeMap<>(Map.of(
                tent.getEquipmentId(), tent,
                bike.getEquipmentId(), bike)));
        when(loanService.getAllLoans()).thenReturn(List.of(tentLoan, firstBikeLoan, secondBikeLoan));

        // Act
        List<Equipment> mostBorrowedEquipment = reportService.getMostBorrowedEquipment();

        // Assert
        assertEquals(List.of(bike), mostBorrowedEquipment);
    }

    @Test
    public void shouldReturnAllEquipmentSharingTheHighestLoanCount() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member member = new Member("fakeMember", MemberStatus.ACTIVE);
        Equipment tent = new Tent("fakeTent", true, 5);
        Equipment bike = new Bike("fakeBike", true, 3);
        Equipment skis = new SkiSet("fakeSkis", true, 7);
        Loan tentLoan = new Loan(member, tent, LocalDate.now(), LocalDate.now().plusDays(5), false);
        Loan bikeLoan = new Loan(member, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);

        when(equipmentService.getAllEquipmentsSortedById()).thenReturn(new TreeMap<>(Map.of(
                tent.getEquipmentId(), tent,
                bike.getEquipmentId(), bike,
                skis.getEquipmentId(), skis)));
        when(loanService.getAllLoans()).thenReturn(List.of(tentLoan, bikeLoan));

        // Act
        List<Equipment> mostBorrowedEquipment = reportService.getMostBorrowedEquipment();

        // Assert
        assertEquals(List.of(tent, bike), mostBorrowedEquipment);
    }

    @Test
    public void shouldNotReturnAnyMostBorrowedEquipmentWhenNothingHasBeenLoaned() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Equipment tent = new Tent("fakeTent", true, 5);
        Equipment bike = new Bike("fakeBike", true, 3);

        when(equipmentService.getAllEquipmentsSortedById()).thenReturn(new TreeMap<>(Map.of(
                tent.getEquipmentId(), tent,
                bike.getEquipmentId(), bike)));
        when(loanService.getAllLoans()).thenReturn(List.of());

        // Act
        List<Equipment> mostBorrowedEquipment = reportService.getMostBorrowedEquipment();

        // Assert
        assertTrue(mostBorrowedEquipment.isEmpty());
    }

    @Test
    public void shouldReturnNeverBorrowedEquipmentEvenWhenItIsUnavailable() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member member = new Member("fakeMember", MemberStatus.ACTIVE);
        Equipment unavailableTent = new Tent("fakeTent", false, 5);
        Equipment returnedBike = new Bike("fakeBike", true, 3);
        Equipment skis = new SkiSet("fakeSkis", true, 7);
        Loan returnedBikeLoan = new Loan(member, returnedBike, LocalDate.now(), LocalDate.now().plusDays(3), false);

        when(equipmentService.getAllEquipmentsSortedById()).thenReturn(new TreeMap<>(Map.of(
                unavailableTent.getEquipmentId(), unavailableTent,
                returnedBike.getEquipmentId(), returnedBike,
                skis.getEquipmentId(), skis)));
        when(loanService.getAllLoans()).thenReturn(List.of(returnedBikeLoan));

        // Act
        List<Equipment> neverBorrowedEquipment = reportService.getNeverBorrowedEquipment();

        // Assert
        assertEquals(List.of(unavailableTent, skis), neverBorrowedEquipment);
    }

    @Test
    public void shouldSortMembersWithMostLoansFirstCountingReturnedLoans() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member memberWithTwoActiveLoans = new Member("fakeMemberOne", MemberStatus.ACTIVE);
        Member memberWithThreeLoans = new Member("fakeMemberTwo", MemberStatus.ACTIVE);
        Equipment bike = new Bike("fakeBike", false, 3);
        Loan firstActiveLoan = new Loan(memberWithTwoActiveLoans, bike, LocalDate.now(), LocalDate.now().plusDays(3), true);
        Loan secondActiveLoan = new Loan(memberWithTwoActiveLoans, bike, LocalDate.now(), LocalDate.now().plusDays(3), true);
        Loan firstReturnedLoan = new Loan(memberWithThreeLoans, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan secondReturnedLoan = new Loan(memberWithThreeLoans, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan thirdActiveLoan = new Loan(memberWithThreeLoans, bike, LocalDate.now(), LocalDate.now().plusDays(3), true);

        when(loanService.getAllLoans()).thenReturn(List.of(
                firstActiveLoan, secondActiveLoan, firstReturnedLoan, secondReturnedLoan, thirdActiveLoan));

        // Act
        List<Member> membersWithMostLoans = reportService.getTopThreeMembersWithMostTotalLoans();

        // Assert
        assertEquals(List.of(memberWithThreeLoans, memberWithTwoActiveLoans), membersWithMostLoans);
    }

    @Test
    public void shouldSortMembersWithSameLoanCountByMemberId() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Member memberWithLowerId = new Member("fakeMemberOne", MemberStatus.ACTIVE);
        Member memberWithHigherId = new Member("fakeMemberTwo", MemberStatus.ACTIVE);
        Equipment bike = new Bike("fakeBike", false, 3);
        Loan loanForHigherId = new Loan(memberWithHigherId, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);
        Loan loanForLowerId = new Loan(memberWithLowerId, bike, LocalDate.now(), LocalDate.now().plusDays(3), false);

        when(loanService.getAllLoans()).thenReturn(List.of(loanForHigherId, loanForLowerId));

        // Act
        List<Member> membersWithMostLoans = reportService.getTopThreeMembersWithMostTotalLoans();

        // Assert
        assertEquals(List.of(memberWithLowerId, memberWithHigherId), membersWithMostLoans);
    }

    @Test
    public void shouldOnlyReturnAvailableEquipment() {
        // Arrange
        LoanService loanService = mock(LoanService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        ReportService reportService = new ReportService(loanService, equipmentService);

        Equipment availableBike = new Bike("fakeBike", true, 3);
        Equipment loanedTent = new Tent("fakeTent", false, 5);
        Equipment availableSkis = new SkiSet("fakeSkis", true, 7);

        when(equipmentService.getAllEquipmentsSortedById()).thenReturn(new TreeMap<>(Map.of(
                availableBike.getEquipmentId(), availableBike,
                loanedTent.getEquipmentId(), loanedTent,
                availableSkis.getEquipmentId(), availableSkis)));

        // Act
        List<Equipment> availableEquipment = reportService.getAvailableEquipment();

        // Assert
        assertEquals(List.of(availableBike, availableSkis), availableEquipment);
    }
}
