package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Loan;

import java.util.HashMap;
import java.util.Map;

public class LoanDB {
    private static int uniqueLoanId = 1000;
    private static Map<Integer, Loan> loanDatabase = new HashMap<>();

    //Getters

    //Setters

    public static void addLoanToDB(Loan loan) {
        loanDatabase.put(uniqueLoanId, loan);
        uniqueLoanId++;
    }

}
