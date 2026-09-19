package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;

import java.util.Map;

public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    //Setters
    public int addEquipment(String name, boolean available, int maxLoanDays, EquipmentTypes type) {
        Equipment createdEquipment =  equipmentRepository.addEquipment(type.getCreator().create(name, available, maxLoanDays));
        return createdEquipment.getEquipmentId();
    }


    // Getters
    public int getUniqueEquipmentId() {
        return Equipment.getUniqueEquipmentId();
    }
    public Equipment getEquipmentById(int id) {
        return getAllEquipment().get(id);
    }

    public Map<Integer, Equipment> getAllEquipment() {
        return equipmentRepository.getEquipmentDatabase();
    }

    public Equipment getEquipmentByName(String name) {
        Map <Integer, Equipment> equipmentMap = getAllEquipment();
        for (Integer id : equipmentMap.keySet()) {
            if (equipmentMap.get(id).getEquipmentName().contains(name)) {
                return equipmentMap.get(id);
            }
        } return null;
    }

    public int getEquipmentByNameToId(String name) {
        Map <Integer, Equipment> equipmentMap = getAllEquipment();
        for (Integer id : equipmentMap.keySet()) {
            if (equipmentMap.get(id).getEquipmentName().contains(name)) {
                return id;
            }
        } return 0;
    }

    public boolean getEquipmentLoanableStatus(int id) {
        Equipment equipment = getEquipmentById(id);
        return equipment.isAvailable();
    }
}
