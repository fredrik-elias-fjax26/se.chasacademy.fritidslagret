package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.db.EquipmentDB;

import java.util.ArrayList;

public class EquipmentHandler {

    public static void addEquipment(String name,  boolean available, int maxLoanDays, EquipmentTypes type) {
        EquipmentDB.addEquipmentToDB(type.create(name, available, maxLoanDays));
    }

    public static int getSizeOfInventory() {
        return EquipmentDB.getEquipmentDatabase().size();
    }

    public static ArrayList<Equipment> getAllEquipment() {
        return EquipmentDB.getEquipmentDatabase();
    }

}
