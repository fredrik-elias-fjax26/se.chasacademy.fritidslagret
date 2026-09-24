package se.chasacademy.fritidslagret.exception;

public class LoanAlreadyReturned extends RuntimeException {
    public LoanAlreadyReturned(String message) {
        super(message);
    }
}
