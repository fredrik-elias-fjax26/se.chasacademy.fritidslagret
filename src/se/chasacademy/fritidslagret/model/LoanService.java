package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.Exceptions.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;

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
        return  uniqueLoanId;
    }

}
