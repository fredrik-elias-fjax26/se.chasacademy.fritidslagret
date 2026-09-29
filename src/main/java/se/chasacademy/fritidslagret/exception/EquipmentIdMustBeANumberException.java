package se.chasacademy.fritidslagret.exception;

public class EquipmentIdMustBeANumberException extends RuntimeException {
    public EquipmentIdMustBeANumberException() {
          String message = "Artikel-Id måste vara en siffra";
          super(message);
    }
}
