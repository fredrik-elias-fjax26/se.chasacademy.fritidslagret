package se.chasacademy.fritidslagret.exception;

public class LoanAlreadyReturned extends RuntimeException {
    public LoanAlreadyReturned() {
        String message = "Lånet är redan återlämnat";
        super(message);
    }
}
