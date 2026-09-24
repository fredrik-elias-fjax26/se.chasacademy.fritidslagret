package se.chasacademy.fritidslagret.exception;

public class LoanReturnException extends RuntimeException {
    public LoanReturnException(String message) {
        super(message);
    }
}
