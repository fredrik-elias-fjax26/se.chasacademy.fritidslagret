package se.chasacademy.fritidslagret.model.loan;


import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.member.Member;

import java.time.LocalDate;

public class Loan {
    private Member member;
    private int loanId;
    private Equipment equipment;
    private LocalDate dateOfLoan;
    private LocalDate dateOfReturn;
    private boolean loanActive;
    private static int uniqueLoanId = 1000;

    //Constructor
    public Loan(Member member, Equipment equipment, LocalDate dateOfLoan, LocalDate dateOfReturn, boolean loanActive) {
        this.member = member;
        this.loanId = uniqueLoanId;
        this.equipment = equipment;
        this.dateOfLoan = dateOfLoan;
        this.dateOfReturn = dateOfReturn;
        this.loanActive = loanActive;
        uniqueLoanId++;
    }

    public int getLoanId() {
        return loanId;
    }

    public static int  getUniqueLoanId() {
        return uniqueLoanId;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

}


