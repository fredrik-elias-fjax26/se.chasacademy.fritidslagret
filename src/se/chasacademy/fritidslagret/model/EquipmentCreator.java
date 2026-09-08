package se.chasacademy.fritidslagret.model;

public interface EquipmentCreator {
    Equipment create(String name, boolean available, int maxLoanDays);
}

