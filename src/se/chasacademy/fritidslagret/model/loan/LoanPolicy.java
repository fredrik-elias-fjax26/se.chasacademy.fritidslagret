package se.chasacademy.fritidslagret.model.loan;

import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;

public class LoanPolicy {

    public static boolean checkLoanPolicy(Member member) throws UserRevokedException {
        if (member.getMemberStatus() == MemberStatus.REVOKED) {
            throw new UserRevokedException("Användaren har brytit mot policy och blivit av med medlemskap");
        } else if (member.getMemberStatus() == MemberStatus.ACTIVE) {
            return true;
        } else {
            return false;
        }
    }
}
