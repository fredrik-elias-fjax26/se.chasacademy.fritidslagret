package se.chasacademy.fritidslagret.exception;

public class LoanIdMustBeAnNumberException extends RuntimeException {
    public LoanIdMustBeAnNumberException() {
        String message = "Lån id måste vara en siffra, Försök igen";
        super(message);
    }
}
