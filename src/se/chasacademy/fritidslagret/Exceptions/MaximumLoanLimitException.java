package se.chasacademy.fritidslagret.Exceptions;

public class MaximumLoanLimitException extends RuntimeException {
    public MaximumLoanLimitException(String message) {
        super(message);
    }
}
