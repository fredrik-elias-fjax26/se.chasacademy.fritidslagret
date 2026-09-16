package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.loan.LoanPolicy;
import se.chasacademy.fritidslagret.model.loan.LoanRules;
import se.chasacademy.fritidslagret.model.member.Member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoanService {
    private static final Map<Integer, Loan> loanDatabase = new HashMap<>();
    private static int uniqueLoanId = 1000;

    public void startLoan(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException, UserRevokedException {

        LoanPolicy.checkLoanPolicy(member);
        LoanRules.checkLoanRules(equipmentId, member);


        Equipment equipmentForLoan = EquipmentService.getEquipmentById(equipmentId);

        LocalDate calculatedReturnDate = LocalDate.now().plusDays(equipmentForLoan.getMaxLoanDays());
        LocalDate loanDate = LocalDate.now();

        Loan loan = new Loan(
                uniqueLoanId,
                member,
                EquipmentService.getEquipmentById(equipmentId),
                loanDate,
                calculatedReturnDate,
                true
        );

        MemberService.addToActiveLoans(member);
        loanDatabase.put(uniqueLoanId, loan);
        EquipmentService.getEquipmentById(equipmentId).setAvailable(false);
        uniqueLoanId++;
    }

    public Loan getLoanById(int id) {
        return loanDatabase.get(id);
    }

    public int getUniqueLoanId() {
        return uniqueLoanId;
    }

}
