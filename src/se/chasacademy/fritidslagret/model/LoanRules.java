package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.Exceptions.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserInactiveException;

public class LoanRules {

    public static void checkLoanRules(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException {
        if (MemberService.getActiveLoans(member) >= 3)
            throw new LoanMaximumLimitException("Maximalt antal lån redan uppnådd");
        if (!EquipmentService.getEquipmentById(equipmentId).isAvailable()) {
            throw new EquipmentNotAvailableException("Produkten inte tillgänglig för utlåning");
        }
        if (member.getMemberStatus() !=  MemberStatus.ACTIVE) {
            throw new UserInactiveException("Medlemmen har ett inaktivt medlemskap!");
        }
    }
}


