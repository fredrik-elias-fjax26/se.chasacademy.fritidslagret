package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.MaximumLoanLimitException;
import se.chasacademy.fritidslagret.Exceptions.NotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoanService {
    private final static Map<Integer, Loan> loanDatabase = new HashMap<>();
    private static int uniqueLoanId = 1000;

    public static void startLoan(int equipmentId, Member member)
            throws NotAvailableException, MaximumLoanLimitException, UserRevokedException {

        try {
            LoanRules.checkLoanRules(equipmentId, member);
            LoanPolicy.checkLoanPolicy(member);

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
            uniqueLoanId++;
        } catch (MaximumLoanLimitException | NotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }

    }

    public static Loan getLoanById(int id) {
        return loanDatabase.get(id);
    }

}
