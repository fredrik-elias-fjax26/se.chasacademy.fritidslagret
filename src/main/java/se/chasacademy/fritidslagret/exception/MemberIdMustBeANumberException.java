package se.chasacademy.fritidslagret.exception;

public class MemberIdMustBeANumberException extends RuntimeException {
    public MemberIdMustBeANumberException() {
        String message = "Medlems-Id måste vara en siffra";
        super(message);
    }
}
