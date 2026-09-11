package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.db.EquipmentDB;

import java.util.Map;

public class EquipmentHandler {

    //Setters
    public static void addEquipment(String name,  boolean available, int maxLoanDays, EquipmentTypes type) {
        EquipmentDB.addEquipmentToDB(type.create(name, available, maxLoanDays));
    }


    // Getters
    public static Equipment getEquipmentById(int id) {
        return getAllEquipment().get(id);
    }

    public static Map<Integer, Equipment> getAllEquipment() {
        Map<Integer, Equipment> readOnlyDB = Map.copyOf(EquipmentDB.getEquipmentDatabase());
        return readOnlyDB;
    }

    public static Equipment getEquipmentByName(String name) {
        Map<Integer, Equipment> searchMap = EquipmentHandler.getAllEquipment();
        for (Integer id : EquipmentHandler.getAllEquipment().keySet()) {
            if (searchMap.get(id).getName().contains(name)) {
                return searchMap.get(id);
            }
        } return null;
    }

    public static boolean getEquipmentLoanableStatus(int id) {
        Equipment equipment = EquipmentHandler.getEquipmentById(id);
        boolean loanable = equipment.isAvailable();
        return loanable;
    }

}
