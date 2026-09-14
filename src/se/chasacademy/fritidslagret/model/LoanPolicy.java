package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;

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
