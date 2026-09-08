package se.chasacademy.fritidslagret.db;

import static se.chasacademy.fritidslagret.model.EquipmentTypes.*;

import se.chasacademy.fritidslagret.model.EquipmentHandler;

public class Startup {
    public static void startUp() {
        EquipmentHandler.addEquipment("Bicycle", true, 30, BIKE);
        EquipmentHandler.addEquipment("8-people-tent", false, 90, TENT);
        EquipmentHandler.addEquipment("SkiSet-for-Children", true, 3, SKISET);
    }
}
