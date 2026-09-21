package se.chasacademy.fritidslagret.exception;

public class UserRevokedException extends RuntimeException {
    public UserRevokedException(String message) {
        super(message);
    }
}
