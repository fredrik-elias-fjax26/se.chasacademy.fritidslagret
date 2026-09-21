package se.chasacademy.fritidslagret.model.equipment;

public interface EquipmentCreator {
    Equipment create(String name, boolean available, int maxLoanDays);
}
