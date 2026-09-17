package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

public class LoanMenuAdd {
    private final LoanService loanService;

    protected LoanMenuAdd(LoanService loanService) {
        this.loanService = loanService;
    }

    protected void addLoan() {
        try {
            // UI till nytt lån ska hamna här

        } catch (LoanMaximumLimitException | EquipmentNotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }
    }
}
