package se.chasacademy.fritidslagret.model;

import java.util.HashMap;
import java.util.Map;

public class EquipmentService {
    private static final Map<Integer, Equipment> equipmentDatabase = new HashMap<>();
    private static int uniqueEquipmentId;

    //Setters
    public static int addEquipment(int equipmentId, String name,  boolean available, int maxLoanDays, EquipmentTypes type) {
        equipmentDatabase.put(uniqueEquipmentId, type.getCreator().create(equipmentId, name, available, maxLoanDays));
        int assignedEquipmentId = uniqueEquipmentId;
        uniqueEquipmentId++;
        return assignedEquipmentId;
    }


    // Getters
    public static int getUniqueEquipmentId() {
        return uniqueEquipmentId;
    }
    public static Equipment getEquipmentById(int id) {
        return getAllEquipment().get(id);
    }

    public static Map<Integer, Equipment> getAllEquipment() {
        return equipmentDatabase;
    }

    public static Equipment getEquipmentByName(String name) {
        for (Integer id : EquipmentService.getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return equipmentDatabase.get(id);
            }
        } return null;
    }

    public static int getEquipmentByNameToId(String name) {
        for (Integer id : EquipmentService.getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return id;
            }
        } return 0;
    }

    public static boolean getEquipmentLoanableStatus(int id) {
        Equipment equipment = equipmentDatabase.get(id);
        return equipment.isAvailable();
    }
}
