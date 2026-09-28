package se.chasacademy.fritidslagret.exception;

public class UserHasDelayedLoansException extends RuntimeException {
    public UserHasDelayedLoansException() {
        String message = "Medlemmen har försenade lån!";
        super(message);
    }
}
