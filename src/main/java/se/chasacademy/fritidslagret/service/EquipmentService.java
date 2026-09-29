package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.SearchTermDontExistsAnyEquipmentException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;

import java.util.*;

public class EquipmentService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }
    //Setters
    public Equipment addEquipment(String name, boolean available, int maxLoanDays, EquipmentTypes type) {
        return equipmentRepository.addEquipment(type.getCreator().create(name, available, maxLoanDays));
    }


    // Getters
    public Equipment getEquipmentById(int id) {
        return getAllEquipment().get(id);
    }

    public Map<Integer, Equipment> getAllEquipment() {
        return equipmentRepository.getEquipmentDatabase();
    }

    public SortedMap<Integer, Equipment> getAllEquipmentsSortedById() {
        return new TreeMap<>(getAllEquipment());
    }

    public List<Equipment> searchForEquipmentByName(String searchTerm) throws SearchTermDontExistsAnyEquipmentException {
        List<Equipment> foundEquipmentMap = new ArrayList<>();
        Map <Integer, Equipment> equipmentMap = getAllEquipment();
        for (Integer id : equipmentMap.keySet()) {
            if (equipmentMap.get(id).getEquipmentName().contains(searchTerm)) {
                foundEquipmentMap.add(equipmentMap.get(id));
            }
        }

        if (foundEquipmentMap.isEmpty()) {
            throw new SearchTermDontExistsAnyEquipmentException(searchTerm);
        }

        return foundEquipmentMap;
    }

    public void deleteEquipment(Equipment equipmentToDelete) {
        equipmentRepository.removeEquipment(equipmentToDelete);
    }
}
