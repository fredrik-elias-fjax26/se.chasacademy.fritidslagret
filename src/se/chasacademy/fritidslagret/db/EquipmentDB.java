package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Equipment;

import java.util.HashMap;
import java.util.Map;

public class EquipmentDB {
    private static final Map<Integer, Equipment> equipmentDatabase = new HashMap<>();
    private static int uniqueEquipmentId = 0;

    //Getters
    public static Map<Integer, Equipment> getEquipmentDatabase() {
        return equipmentDatabase;
    }

    //Setters

    public static void addEquipmentToDB(Equipment equipment) {
        equipmentDatabase.put(uniqueEquipmentId,  equipment);
        uniqueEquipmentId++;
    }
}