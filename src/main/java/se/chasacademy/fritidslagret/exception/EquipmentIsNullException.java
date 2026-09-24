package se.chasacademy.fritidslagret.exception;

public class EquipmentIsNullException extends RuntimeException {
    public EquipmentIsNullException() {
        String message = "Artikeln finns inte, Försök igen!";
        super(message);
    }
}
