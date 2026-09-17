package se.chasacademy.fritidslagret.model.loan;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserInactiveException;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.MemberService;

public class LoanRules {
    private final MemberService memberService;
    private final EquipmentService equipmentService;

    public LoanRules(MemberService memberService, EquipmentService equipmentService) {
        this.memberService = memberService;
        this.equipmentService = equipmentService;
    }

    public void checkLoanRules(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException {
        if (memberService.getActiveLoans(member) >= 3)
            throw new LoanMaximumLimitException("Maximalt antal lån redan uppnådd");
        if (!equipmentService.getEquipmentById(equipmentId).isAvailable()) {
            throw new EquipmentNotAvailableException("Produkten inte tillgänglig för utlåning");
        }
        if (member.getMemberStatus() !=  MemberStatus.ACTIVE) {
            throw new UserInactiveException("Medlemmen har ett inaktivt medlemskap!");
        }
    }
}


