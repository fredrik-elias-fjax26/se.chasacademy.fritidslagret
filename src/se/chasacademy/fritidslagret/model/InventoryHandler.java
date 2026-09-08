package se.chasacademy.fritidslagret.model;

public class InventoryHandler {
    private static final Equipment[] equipmentDatabase = new Equipment[500];
    private static int idCounter = 0;

    public static void addEquipment(Class<?> type, String name,  boolean available, int maxLoanDays) {
        EquipmentTypes created = EquipmentTypes.(type, name, available, maxLoanDays);
        equipmentDatabase[idCounter] = type.class();
        idCounter++;
    }

    public static Equipment[] getAllEquipments() {
        return equipmentDatabase;
    }

    public static int getIdCounter() {
        return idCounter;
    }

}
