package se.chasacademy.fritidslagret.exception;

public class NoLoanExistsForMemberSearchException extends RuntimeException {
    public NoLoanExistsForMemberSearchException() {
        String message = "Sökningen gav inga träffar!";
        super(message);
    }
}
