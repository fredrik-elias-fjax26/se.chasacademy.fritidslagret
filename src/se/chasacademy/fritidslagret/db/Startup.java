package se.chasacademy.fritidslagret.db;

import static se.chasacademy.fritidslagret.model.EquipmentTypes.*;

import se.chasacademy.fritidslagret.model.EquipmentService;
import se.chasacademy.fritidslagret.model.MemberService;

import static se.chasacademy.fritidslagret.model.MemberStatus.*;

public class Startup {
    public static void startUp() {
        // Equipment start
        EquipmentService.addEquipment("Bicycle", true, 30, BIKE);
        EquipmentService.addEquipment("8-people-tent", false, 90, TENT);
        EquipmentService.addEquipment("SkiSet-for-Children", true, 3, SKISET);

        // Users start

        MemberService.addMember("Ylva", ACTIVE);
        MemberService.addMember("Nisse", REVOKED);
        MemberService.addMember("Bosse", INACTIVE);


    }
}
