package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;

public class LoanAdd {
    public static void addLoan() {
        try {
            // UI till nytt lån ska hamna här

        } catch (LoanMaximumLimitException | EquipmentNotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }
    }
}
