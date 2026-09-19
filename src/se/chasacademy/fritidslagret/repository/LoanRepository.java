package se.chasacademy.fritidslagret.repository;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;

import java.util.HashMap;
import java.util.Map;

public class LoanRepository {
    private final Map<Integer, Loan> loanDatabase = new HashMap<>();
    private static LoanRepository instance = null;

    private LoanRepository() {}

    public static LoanRepository getInstance() {
        if (instance == null) {
            instance = new LoanRepository();
        }
        return instance;
    }

    public Map<Integer, Loan> getLoanDatabase() {
        return loanDatabase;
    }

    public Loan addLoan(Loan loan) {
        int loanId =  loan.getLoanId();
        loanDatabase.put(loanId, loan);
        return loan;
    }

    public void removeLoan(Loan  loan) {
        loanDatabase.remove(loan.getLoanId());
    }
}
