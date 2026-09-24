package se.chasacademy.fritidslagret.exception;

public class MemberIsNullException extends RuntimeException {
    public MemberIsNullException() {
        String message = "Medlemmen är finns inte!";
        super(message);
    }
}
