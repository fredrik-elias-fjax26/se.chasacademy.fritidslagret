package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.Exceptions.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.Exceptions.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;

public class LoanAdd {
    public static void addLoan() {
        try {
            // UI till nytt lån ska hamna här

        } catch (LoanMaximumLimitException | EquipmentNotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }
    }
}
