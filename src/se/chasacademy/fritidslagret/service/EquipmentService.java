package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;

import java.util.HashMap;
import java.util.Map;

public class EquipmentService {
    private final Map<Integer, Equipment> equipmentDatabase = new HashMap<>();
    private static int uniqueEquipmentId = 0;

    //Setters
    public int addEquipment(String name, boolean available, int maxLoanDays, EquipmentTypes type) {
        int assignedEquipmentId = uniqueEquipmentId;

        equipmentDatabase.put(uniqueEquipmentId, type.getCreator().create(assignedEquipmentId, name, available, maxLoanDays));
        uniqueEquipmentId++;
        return assignedEquipmentId;
    }


    // Getters
    public int getUniqueEquipmentId() {
        return uniqueEquipmentId;
    }
    public Equipment getEquipmentById(int id) {
        return getAllEquipment().get(id);
    }

    public Map<Integer, Equipment> getAllEquipment() {
        return equipmentDatabase;
    }

    public Equipment getEquipmentByName(String name) {
        for (Integer id : getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return equipmentDatabase.get(id);
            }
        } return null;
    }

    public int getEquipmentByNameToId(String name) {
        for (Integer id : getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return id;
            }
        } return 0;
    }

    public boolean getEquipmentLoanableStatus(int id) {
        Equipment equipment = equipmentDatabase.get(id);
        return equipment.isAvailable();
    }
}
