package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Equipment;

public class InventoryHandler {
    private static final Equipment[] equipmentDatabase = new Equipment[500];
    private static int idCounter = 0;

    public static void addEquipment(Equipment equipment) {
        equipmentDatabase[idCounter] = equipment;
        idCounter++;
    }

    public static Equipment[] getAllEquipments() {
        return equipmentDatabase;
    }

    public static int getIdCounter() {
        return idCounter;
    }
}
