package se.chasacademy.fritidslagret.exception;

public class LoanDoesNotExistException extends RuntimeException {
    public LoanDoesNotExistException() {
        String message = "Lånet finns inte!";
        super(message);
    }
}
