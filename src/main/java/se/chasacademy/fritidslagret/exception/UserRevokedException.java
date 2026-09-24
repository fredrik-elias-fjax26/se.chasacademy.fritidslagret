package se.chasacademy.fritidslagret.exception;

public class UserRevokedException extends RuntimeException {
    public UserRevokedException() {
        String message = "Användaren har brytit mot policy och blivit av med medlemskap";
        super(message);
    }
}
