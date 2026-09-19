package se.chasacademy.fritidslagret.repository;

import se.chasacademy.fritidslagret.model.equipment.Equipment;

import java.util.HashMap;
import java.util.Map;

public class EquipmentRepository {
    private final Map<Integer, Equipment> equipmentDatabase = new HashMap<>();
    private static EquipmentRepository instance = null;

    private EquipmentRepository() {}

    public static EquipmentRepository getInstance() {
        if (instance == null) {
            instance = new EquipmentRepository();
        }
        return instance;
    }

    public Map<Integer, Equipment> getEquipmentDatabase() {
        return Map.copyOf(equipmentDatabase);
    }

    public Equipment addEquipment(Equipment equipment) {
        int equipmentId =  equipment.getEquipmentId();
        equipmentDatabase.put(equipmentId, equipment);
        return equipment;
    }

    public void removeEquipment(Equipment equipment) {
        equipmentDatabase.remove(equipment.getEquipmentId());
    }
}
