package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.loan.LoanPolicy;
import se.chasacademy.fritidslagret.model.loan.LoanRules;
import se.chasacademy.fritidslagret.model.member.Member;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoanService {
    private final Clock clock;
    private final Map<Integer, Loan> loanDatabase = new HashMap<>();
    private static int uniqueLoanId = 1000;
    private final MemberService memberService;
    private final EquipmentService equipmentService;
    private final LoanRules loanRules;
    private final LoanPolicy loanPolicy;

    public LoanService(MemberService memberService, EquipmentService equipmentService, Clock clock) {
        this.clock = clock;
        this.memberService = memberService;
        this.equipmentService = equipmentService;
        loanPolicy = new LoanPolicy();
        loanRules = new LoanRules(memberService, equipmentService);
    }

    public void startLoan(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException, UserRevokedException {

        loanPolicy.checkLoanPolicy(member);
        loanRules.checkLoanRules(equipmentId, member);


        Equipment equipmentForLoan = equipmentService.getEquipmentById(equipmentId);

        LocalDate calculatedReturnDate = LocalDate.now(clock).plusDays(equipmentForLoan.getMaxLoanDays());
        LocalDate loanDate = LocalDate.now();

        Loan loan = new Loan(
                uniqueLoanId,
                member,
                equipmentService.getEquipmentById(equipmentId),
                loanDate,
                calculatedReturnDate,
                true
        );

        memberService.addToActiveLoans(member);
        loanDatabase.put(uniqueLoanId, loan);
        equipmentService.getEquipmentById(equipmentId).setAvailable(false);
        uniqueLoanId++;
    }

    public Loan getLoanById(int id) {
        return loanDatabase.get(id);
    }

    public int getUniqueLoanId() {
        return uniqueLoanId;
    }

    public boolean isDelayed(Loan loan){
        if (loan.getDateOfReturn().isBefore(LocalDate.now(clock))){
            return true;
        }
        return false;
    }

    public Map<Integer, Loan> getExpiredLoans() {
        Map<Integer, Loan> expiredLoanList = new HashMap<>();

        for (Loan loan : loanDatabase.values()) {
            if (loan.getDateOfReturn().isBefore(LocalDate.now(clock) )){
                int id = loan.getLoanId();
                expiredLoanList.put(id , loan);
            }
        }
        return expiredLoanList;
    }

}
