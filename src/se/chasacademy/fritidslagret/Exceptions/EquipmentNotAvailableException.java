package se.chasacademy.fritidslagret.Exceptions;

public class EquipmentNotAvailableException extends RuntimeException {
    public EquipmentNotAvailableException(String message) {
        super(message);
    }
}
