package se.chasacademy.fritidslagret.model;

public interface EquipmentCreator {
    Equipment create(int equipmentId, String name, boolean available, int maxLoanDays);
}
