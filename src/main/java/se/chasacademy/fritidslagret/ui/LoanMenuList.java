package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.LoanService;

public class LoanMenuList {
    private final LoanService loanService;

    protected LoanMenuList(LoanService loanService) {
        this.loanService = loanService;
    }

    protected void listLoans() {

    }
}
