package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.MaximumLoanLimitException;
import se.chasacademy.fritidslagret.Exceptions.NotAvailableException;

public class LoanRules {

    public static void checkLoanRules(int equipmentId, Member member)
            throws NotAvailableException, MaximumLoanLimitException {
        if (MemberService.getActiveLoans(member) >= 3)
            throw new MaximumLoanLimitException("Maximalt antal lån redan uppnådd");
        if (!EquipmentService.getEquipmentById(equipmentId).isAvailable()) {
            throw new NotAvailableException("Produkten inte tillgänglig för utlåning");
        }
    }
}


