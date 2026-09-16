package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoanService {
    private final static Map<Integer, Loan> loanDatabase = new HashMap<>();
    private static int uniqueLoanId = 1000;

    public static void startLoan(int equipmentId, Member member)
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

    public static Loan getLoanById(int id) {
        return loanDatabase.get(id);
    }

    public static int getUniqueLoanId() {
        return uniqueLoanId;
    }

    public static boolean isDelayed(int id){
        if (loanDatabase.get(id).getDateOfReturn().isBefore(LocalDate.now())){
            return true;
        }
        return false;
    }

    public static Map<Integer, Loan> getExpiredLoans() {
        Map<Integer, Loan> expiredLoanList = new HashMap<>();

        for (Loan loan : loanDatabase.values()) {
            if (loan.getDateOfReturn().isAfter(LocalDate.now() )){
                int id = loan.getLoanId();
                expiredLoanList.put(id , loan);
            }
        }
        return expiredLoanList;
    }

}
