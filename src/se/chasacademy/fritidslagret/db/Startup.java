package se.chasacademy.fritidslagret.db;

import static se.chasacademy.fritidslagret.model.EquipmentTypes.*;

import se.chasacademy.fritidslagret.model.EquipmentHandler;
import se.chasacademy.fritidslagret.model.MemberHandler;
import static se.chasacademy.fritidslagret.model.MemberStatus.*;

public class Startup {
    public static void startUp() {
        // Equipment start
        EquipmentHandler.addEquipment("Bicycle", true, 30, BIKE);
        EquipmentHandler.addEquipment("8-people-tent", false, 90, TENT);
        EquipmentHandler.addEquipment("SkiSet-for-Children", true, 3, SKISET);

        // Users start

        MemberHandler.addMember("Ylva", ACTIVE);
        MemberHandler.addMember("Nisse", REVOKED);
        MemberHandler.addMember("Bosse", INACTIVE);


    }
}
