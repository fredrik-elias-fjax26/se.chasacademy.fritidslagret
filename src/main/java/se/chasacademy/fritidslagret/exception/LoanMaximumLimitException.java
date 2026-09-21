package se.chasacademy.fritidslagret.exception;

public class LoanMaximumLimitException extends RuntimeException {
    public LoanMaximumLimitException(String message) {
        super(message);
    }
}
