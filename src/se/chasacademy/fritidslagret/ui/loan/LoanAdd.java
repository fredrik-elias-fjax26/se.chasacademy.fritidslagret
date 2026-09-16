package se.chasacademy.fritidslagret.ui.loan;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.MemberService;

public class LoanAdd {
    private LoanAdd () {
        EquipmentService equipmentService =  new EquipmentService();
        MemberService memberService =  new MemberService();
        }

    public static void addLoan() {
        try {
            // UI till nytt lån ska hamna här

        } catch (LoanMaximumLimitException | EquipmentNotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }
    }
}
