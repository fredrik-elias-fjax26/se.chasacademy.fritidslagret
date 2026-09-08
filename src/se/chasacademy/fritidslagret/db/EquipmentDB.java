package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDB {
    private static final ArrayList<Equipment> equipmentDatabase = new ArrayList<>();

    //Getters
    public static ArrayList<Equipment> getEquipmentDatabase() {
        return equipmentDatabase;
    }

    //Setters

    public static void addEquipmentToDB(Equipment equipment) {
        equipmentDatabase.add(equipment);
    }
}