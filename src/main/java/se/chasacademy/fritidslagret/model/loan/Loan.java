package se.chasacademy.fritidslagret.model.loan;


import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.member.Member;

import java.time.LocalDate;

public class Loan {
    private final Member member;
    private final int loanId;
    private final Equipment equipment;
    private final LocalDate dateOfLoan;
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

    public Member getMember() {
        return member;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public LocalDate getDateOfLoan() {
        return dateOfLoan;
    }

    public LocalDate getLocalDateOfReturn() {
        return dateOfReturn;
    }
    public boolean isLoanActive() {
        return loanActive;
    }

    public void setLoanActive(boolean loanActive) {
        this.loanActive = loanActive;
    }

    public static int  getUniqueLoanId() {
        return uniqueLoanId;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    @Override
    public String toString(){
        return String.format("Lån-ID: %d, Medlem: %s, Artikel: %s, Lånedatum: %s, Senaste datum för återlämning: %s",
                getLoanId(), getMember().getName(), getEquipment().getEquipmentName(),
                getDateOfLoan().toString(), getDateOfReturn().toString());
    }
}


