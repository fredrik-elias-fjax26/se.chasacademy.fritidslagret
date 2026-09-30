package se.chasacademy.fritidslagret.exception;

public class CantDeleteEquipmentWithActiveLoanException extends RuntimeException {
    public CantDeleteEquipmentWithActiveLoanException() {
        String message = "Artikeln har ett aktivt lån, radering ej möjlig!!";
        super(message);
    }
}
