package se.chasacademy.fritidslagret;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

import static se.chasacademy.fritidslagret.model.equipment.EquipmentTypes.*;
import static se.chasacademy.fritidslagret.model.member.MemberStatus.*;

public class Startup {
    private final EquipmentService equipmentService;
    private final MemberService memberService;
    private final LoanService loanService;

    public Startup(EquipmentService equipmentService, MemberService memberService, LoanService loanService) {
        this.equipmentService = equipmentService;
        this.memberService = memberService;
        this.loanService = loanService;
    }
    public void startUp() {


        // Equipment start
        equipmentService.addEquipment("Bicycle", true, 30, BIKE);
        equipmentService.addEquipment("8-people-tent", false, 90, TENT);
        equipmentService.addEquipment("SkiSet-for-Children", true, 3, SKISET);
        equipmentService.addEquipment("SkiSet-for-Small-Children", true, 10, SKISET);
        equipmentService.addEquipment("Motorcycle", true, 180, BIKE);
        // Users start

        memberService.addMember("Ylva", ACTIVE);
        memberService.addMember("Nisse", REVOKED);
        memberService.addMember("Bosse", INACTIVE);
        memberService.addMember("NoLoanInitMember", ACTIVE);

        // Loans start
        //loanService.startLoan(2, memberService.getMember(1000));
        loanService.startLoan(3, memberService.getMember(1000));
        loanService.startLoan(4, memberService.getMember(1000));
        //LoanService.startLoan(1, MemberService.getMember(1000));
    }
}
