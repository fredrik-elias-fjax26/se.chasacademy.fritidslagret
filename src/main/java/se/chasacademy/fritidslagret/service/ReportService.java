package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportService {
    private final LoanService loanService;
    private final EquipmentService equipmentService;

    public ReportService(LoanService loanService, EquipmentService equipmentService) {
        this.loanService = loanService;
        this.equipmentService = equipmentService;
    }

    public List<Loan> getActiveLoans() {
        return List.copyOf(loanService.getAllActiveLoansSortedById().values());
    }

    public List<Equipment> getMostBorrowedEquipment() {
        List<Equipment> mostBorrowedEquipment = new ArrayList<>();
        // Starts at 1 so equipment that has never been loaned can't be the most borrowed
        int highestLoanCount = 1;

        for (Equipment equipment : equipmentService.getAllEquipmentsSortedById().values()) {
            int loanCount = getLoanCountForEquipment(equipment);
            if (loanCount > highestLoanCount) {
                highestLoanCount = loanCount;
                mostBorrowedEquipment.clear();
            }
            if (loanCount == highestLoanCount) {
                mostBorrowedEquipment.add(equipment);
            }
        }
        return mostBorrowedEquipment;
    }

    public List<Equipment> getNeverBorrowedEquipment() {
        List<Equipment> neverBorrowedEquipment = new ArrayList<>();
        for (Equipment equipment : equipmentService.getAllEquipmentsSortedById().values()) {
            if (getLoanCountForEquipment(equipment) == 0) {
                neverBorrowedEquipment.add(equipment);
            }
        }
        return neverBorrowedEquipment;
    }

    public List<Member> getTopThreeMembersWithMostTotalLoans() {
        List<Member> membersWithLoans = new ArrayList<>();
        for (Loan loan : loanService.getAllLoans()) {
            if (!membersWithLoans.contains(loan.getMember())) {
                membersWithLoans.add(loan.getMember());
            }
        }
        membersWithLoans.sort(Comparator.comparing(this::getLoanCountForMember).reversed()
                .thenComparing(Member::getMemberId));

        List<Member> topThreeMembersWithMostTotalLoans = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            if (membersWithLoans.size() <= i) {
                continue;
            }
            
            topThreeMembersWithMostTotalLoans.add(membersWithLoans.get(i));
        }
        
        return topThreeMembersWithMostTotalLoans;
    }

    public List<Equipment> getAvailableEquipment() {
        List<Equipment> availableEquipment = new ArrayList<>();
        for (Equipment equipment : equipmentService.getAllEquipmentsSortedById().values()) {
            if (equipment.isAvailable()) {
                availableEquipment.add(equipment);
            }
        }
        return availableEquipment;
    }

    public int getLoanCountForEquipment(Equipment equipment) {
        int loanCount = 0;
        for (Loan loan : loanService.getAllLoans()) {
            if (loan.getEquipment().getEquipmentId() == equipment.getEquipmentId()) {
                loanCount++;
            }
        }
        return loanCount;
    }

    public int getLoanCountForMember(Member member) {
        int loanCount = 0;
        for (Loan loan : loanService.getAllLoans()) {
            if (loan.getMember().getMemberId() == member.getMemberId()) {
                loanCount++;
            }
        }
        return loanCount;
    }

    public String isDelayed(Loan loan) {
        boolean delayed = loanService.isDelayed(loan);
        if (delayed) {
            return "Ja";
        }
        return "Nej";
    }
}
