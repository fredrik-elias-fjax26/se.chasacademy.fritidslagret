package se.chasacademy.fritidslagret.model;

import java.util.Date;

public class Loan {
    private int memberId;
    private int loanId;
    private Equipment equipment;
    private Date dateOfLoan;
    private Date dateOfReturn;
    private boolean loanActive;

    public Loan(int memberId, int loanId, Equipment equipment, Date dateOfLoan,  Date dateOfReturn, boolean loanActive,
                String statusOfReturn) {
        this.memberId = memberId;
        this.loanId = loanId;
        this.equipment = equipment;
        this.dateOfLoan = dateOfLoan;
        this.dateOfReturn = dateOfReturn;
        this.loanActive = loanActive;
    }

    public void startLoan() {




        /* if (loanActive) {
            IO.println("Artikeln redan utlånad!!");
        }
        for (int i = 0; i < Loan.size(); i++);


        if () {
            ;
        }*/
    }


}
