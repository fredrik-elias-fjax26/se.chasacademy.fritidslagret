package se.chasacademy.fritidslagret.exception;

public class LoanMaximumLimitException extends RuntimeException {
    public LoanMaximumLimitException() {
        String message = "Maximalt antal lån redan uppnådd";
        super(message);
    }
}
