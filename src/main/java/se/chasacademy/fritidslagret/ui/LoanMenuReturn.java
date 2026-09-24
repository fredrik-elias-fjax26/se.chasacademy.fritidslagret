package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.LoanReturnException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.LoanService;

public class LoanMenuReturn {
    private final LoanService loanService;

    protected LoanMenuReturn(LoanService loanService) {
        this.loanService = loanService;
    }

    void returnLoan() {
        IO.println("Vilket lån vill du lämna tillbaka?");
        int loanId = Integer.parseInt(IO.readln("Ange Låne-ID: "));
        Loan loanToReturn = loanService.getLoanById(loanId);
        Equipment equipment = loanToReturn.getEquipment();
        boolean isReturned = loanService.returnLoan(loanToReturn);

        if (!isReturned) {
            throw new LoanReturnException("Lånet kunde inte återlämnas.");
        } else {
            IO.println(String.format("%s är nu återlämnad", equipment.getEquipmentName()));
        }
    }
}
