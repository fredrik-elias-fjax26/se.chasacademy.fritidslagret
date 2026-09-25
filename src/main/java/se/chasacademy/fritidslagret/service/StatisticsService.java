package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.equipment.Equipment;

public class StatisticsService {
    private final EquipmentService equipmentService;
    private final LoanService loanService;
    private final MemberService memberService;

    public StatisticsService(EquipmentService equipmentService, LoanService loanService, MemberService memberService){
        this.equipmentService = equipmentService;
        this.loanService = loanService;
        this.memberService = memberService;
    }

    public void showActiveLoanCount() {
        IO.println("Aktiva lån: " + loanService.getActiveLoanCount());
    }

    public void showMemberCount(){
        IO.println("Aktiva medlemmar: " + memberService.getMembers().size());
    }

    public void showMostBorrowedEquipment() {
        Equipment mostBorrowed = loanService.getMostBorrowedEquipment();
        if (mostBorrowed == null) {
            IO.println("Inga lån genomförda ännu");
        } else {
            IO.println("Mest lånade utrustning: " + mostBorrowed.getEquipmentName());
        }
    }
    public void showAvailableEquipmentCount() {
        IO.println("");
        int count = 0;
        for (Equipment equipment : equipmentService.getAllEquipment().values()) {
            if (equipment.isAvailable()) {
                IO.println(equipment.getEquipmentName() + " (" + equipment.getType() + ") - max "
                        + equipment.getMaxLoanDays() + " dagar");
                count++;
            }
        }
        IO.println("Tillgänglig utrustning: " + count);
    }
}
