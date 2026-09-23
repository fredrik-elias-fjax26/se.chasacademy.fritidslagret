package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.service.LoanService;

public class LoanMenuReturn {
    private final LoanService loanService;

     LoanMenuReturn(LoanService loanService) {
        this.loanService = loanService;
    }

     void returnLoan() {
         IO.println("Vilket lån vill du lämna tillbaka?");
         int loanId = Integer.parseInt(IO.readln("Ange Låne-ID: "));
         Loan loanToReturn = loanService.getLoanById(loanId);
         Equipment equipment = loanToReturn.getEquipment();
         Loan newLoanFromQueue = loanService.returnLoan(loanToReturn);

         if (newLoanFromQueue != null) {
             Member memberInNewLoan = newLoanFromQueue.getMember();
             IO.println(String.format("%s har nu lånat %s istället", memberInNewLoan.getName(), equipment.getEquipmentName()));
         } else {
             IO.println(String.format("%s är nu återlämnad", equipment.getEquipmentName()));
         }
    }
}
