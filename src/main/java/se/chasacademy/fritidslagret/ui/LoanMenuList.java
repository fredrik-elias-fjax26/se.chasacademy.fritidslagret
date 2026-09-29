package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.LoanService;

import java.util.List;
import java.util.SortedMap;

public class LoanMenuList {
    private final LoanService loanService;

    protected LoanMenuList(LoanService loanService) {
        this.loanService = loanService;
    }

    protected void listLoans() {
        SortedMap<Integer, Loan> activeLoanList = loanService.getAllActiveLoansSortedById();
        if (activeLoanList.isEmpty()) {
            IO.println("Inga aktiva lån finns");
        } else {
            for (Loan loan : activeLoanList.values()) {
                IO.println(loan);
            }
        }
    }

    public void listDelayedLoans() {
        List<Loan> delayedLoans = loanService.getDelayedLoansSortedByDate();
        if (delayedLoans.isEmpty()) {
            IO.println("Inga lån är försenade :-D");
        } else {
            IO.println("Försenad:\n");
            for (Loan loan : delayedLoans) {
                IO.println(String.format("Antal dagar försenad: %d, Återlämningsdatum: %s, Lån-ID: %d, Medlem: %s",
                        loanService.getDelayedDays(loan),
                        loan.getDateOfReturn(),
                        loan.getLoanId(),
                        loan.getMember().getName()
                ));
            }
        }
    }
}
