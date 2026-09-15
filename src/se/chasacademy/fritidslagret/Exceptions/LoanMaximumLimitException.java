package se.chasacademy.fritidslagret.Exceptions;

public class LoanMaximumLimitException extends RuntimeException {
    public LoanMaximumLimitException(String message) {
        super(message);
    }
}
