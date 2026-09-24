package se.chasacademy.fritidslagret.exception;

public class EquipmentNotAvailableException extends RuntimeException {
    public EquipmentNotAvailableException() {
        String message = "Artikeln är redan utlånad!";
        super(message);
    }
}
