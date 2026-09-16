package se.chasacademy.fritidslagret;

import static se.chasacademy.fritidslagret.model.EquipmentTypes.*;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

import static se.chasacademy.fritidslagret.model.MemberStatus.*;

public class Startup {
    public static void startUp() {


        // Equipment start
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Bicycle", true, 30, BIKE);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "8-people-tent", false, 90, TENT);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "SkiSet-for-Children", true, 3, SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "SkiSet-for-Small-Children", true, 10, SKISET);
        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), "Motorcycle", true, 180, BIKE);
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
