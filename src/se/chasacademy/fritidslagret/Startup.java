package se.chasacademy.fritidslagret;

import static se.chasacademy.fritidslagret.model.equipment.EquipmentTypes.*;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

import static se.chasacademy.fritidslagret.model.member.MemberStatus.*;

public class Startup {
    static MemberService memberService =  new MemberService();
    static LoanService loanService =  new LoanService();
    static EquipmentService equipmentService =  new EquipmentService();

    public static void startUp() {

        // Equipment start
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Bicycle", true, 30, BIKE);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "8-people-tent", false, 90, TENT);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "SkiSet-for-Children", true, 3, SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "SkiSet-for-Small-Children", true, 10, SKISET);
        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), "Motorcycle", true, 180, BIKE);
        // Users start

        MemberService.addMember("Ylva", ACTIVE);
        MemberService.addMember("Nisse", REVOKED);
        MemberService.addMember("Bosse", INACTIVE);

        // Loans start
        LoanService.startLoan(4, MemberService.getMember(1000));
        LoanService.startLoan(2, MemberService.getMember(1000));
        LoanService.startLoan(3, MemberService.getMember(1000));
        //LoanService.startLoan(0, MemberService.getMember(1000));
    }
}
