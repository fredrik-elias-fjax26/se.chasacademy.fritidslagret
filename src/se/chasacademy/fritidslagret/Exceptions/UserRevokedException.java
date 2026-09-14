package se.chasacademy.fritidslagret.Exceptions;

public class UserRevokedException extends RuntimeException {
    public UserRevokedException(String message) {
        super(message);
    }
}
