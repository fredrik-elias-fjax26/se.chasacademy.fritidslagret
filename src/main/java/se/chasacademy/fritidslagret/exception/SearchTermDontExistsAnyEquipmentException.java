package se.chasacademy.fritidslagret.exception;

public class SearchTermDontExistsAnyEquipmentException extends RuntimeException {
    public SearchTermDontExistsAnyEquipmentException(String searchTerm) {
        String message = String.format("Ingen artikel hittades för din sökning: %s", searchTerm);
        super(message);
    }
}
