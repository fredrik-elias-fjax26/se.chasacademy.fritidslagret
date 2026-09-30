package se.chasacademy.fritidslagret.exception;

public class ConfirmationFailedException extends RuntimeException {
    public ConfirmationFailedException() {
        String message = "Felaktig bekräftelse. Avbryter!!!";
        super(message);
    }
}
