package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.LoanService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
                IO.println(String.format(
                        "Lån-ID: %d, Medlem: %s, Artikel: %s, Lånedatum: %s, Senaste datum för återlämning: %s",
                        loan.getLoanId(), loan.getMember().getName(), loan.getEquipment().getEquipmentName(),
                        loan.getDateOfLoan().toString(), loan.getDateOfReturn().toString()));
            }
        }
    }

    public void listDelayedLoans() {
        List<Loan> delayedLoans = loanService.getDelayedLoansSortedByDate();
        if (delayedLoans.isEmpty()) {
            IO.println("Inga lån är försenade :-D");
        } else {
            IO.println("Försenad:\n");
            LocalDate runDate = LocalDate.now();
            for (Loan loan : delayedLoans) {
                LocalDate dateOfReturn = loan.getDateOfReturn();
                IO.println(String.format("Antal dagar försenad: %d, Återlämningsdatum: %s, Lån-ID: %d, Medlem: %s", (ChronoUnit.DAYS.between(dateOfReturn, runDate)), dateOfReturn, loan.getLoanId(), loan.getMember().getName()));
            }
        }
    }
}
