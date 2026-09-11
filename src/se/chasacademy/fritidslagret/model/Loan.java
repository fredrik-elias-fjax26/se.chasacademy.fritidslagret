package se.chasacademy.fritidslagret.model;

import java.util.Date;

public class Loan {
    private Member member;
    private Equipment equipment;
    private Date dateOfLoan;
    private Date dateOfReturn;
    private boolean loanActive;

    //Constructor
    public Loan(Member member, Equipment equipment, Date dateOfLoan, Date dateOfReturn, boolean loanActive) {
        this.member = member;
        this.equipment = equipment;
        this.dateOfLoan = dateOfLoan;
        this.dateOfReturn = dateOfReturn;
        this.loanActive = loanActive;
    }
}

