package se.chasacademy.fritidslagret.model.loan;

import se.chasacademy.fritidslagret.exception.UserHasDelayedLoansException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.service.LoanService;

public class LoanPolicy {
    LoanService loanService;

    public LoanPolicy(LoanService loanService) {
        this.loanService = loanService;
    }

    public void checkLoanPolicy(Member member) throws UserRevokedException {
        if (member.getMemberStatus() == MemberStatus.REVOKED) {
            throw new UserRevokedException();
        }
        if (loanService.memberHasDelayedLoansById(member.getMemberId())) {
            throw new UserHasDelayedLoansException();
        }
    }
}
