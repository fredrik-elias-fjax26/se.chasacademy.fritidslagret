package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;

import java.util.HashMap;
import java.util.Map;

public class EquipmentService {
    private static final Map<Integer, Equipment> equipmentDatabase = new HashMap<>();
    private static int uniqueEquipmentId = 0;

    public EquipmentService() {
    }

    //Setters
    public int addEquipment(int equipmentId, String name, boolean available, int maxLoanDays, EquipmentTypes type) {
        equipmentDatabase.put(uniqueEquipmentId, type.getCreator().create(equipmentId, name, available, maxLoanDays));
        int assignedEquipmentId = uniqueEquipmentId;
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
        for (Integer id : EquipmentService.getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return equipmentDatabase.get(id);
            }
        } return null;
    }

    public int getEquipmentByNameToId(String name) {
        for (Integer id : EquipmentService.getAllEquipment().keySet()) {
            if (equipmentDatabase.get(id).getEquipmentName().contains(name)) {
                return id;
            }
        } return 0;
    }
}
