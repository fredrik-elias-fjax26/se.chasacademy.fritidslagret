package se.chasacademy.fritidslagret.model;


import java.time.LocalDate;

public class Loan {
    private Member member;
    private int loanId;
    private Equipment equipment;
    private LocalDate dateOfLoan;
    private LocalDate dateOfReturn;
    private boolean loanActive;

    //Constructor
    public Loan(int assignedLoanId, Member member, Equipment equipment, LocalDate dateOfLoan, LocalDate dateOfReturn, boolean loanActive) {
        this.member = member;
        this.loanId = assignedLoanId;
        this.equipment = equipment;
        this.dateOfLoan = dateOfLoan;
        this.dateOfReturn = dateOfReturn;
        this.loanActive = loanActive;
    }

    public int getLoanId() {
        return loanId;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }
}


