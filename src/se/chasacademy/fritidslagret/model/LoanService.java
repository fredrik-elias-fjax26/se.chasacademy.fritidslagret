package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.MaximumLoanLimitException;
import se.chasacademy.fritidslagret.Exceptions.NotAvailableException;
import se.chasacademy.fritidslagret.Exceptions.UserRevokedException;
import se.chasacademy.fritidslagret.db.LoanDB;

import java.time.LocalDate;

public class LoanService {

    public static void startLoan(int equipmentId, Member member)
            throws NotAvailableException, MaximumLoanLimitException, UserRevokedException {

        try {
            LoanRules.checkLoanRules(equipmentId, member);
            LoanPolicy.checkLoanPolicy(member);

            Equipment equipmentForLoan = EquipmentService.getEquipmentById(equipmentId);

            LocalDate calculatedReturnDate = LocalDate.now().plusDays(equipmentForLoan.getMaxLoanDays());
            LocalDate loanDate = LocalDate.now();

            Loan loan = new Loan(
                    member,
                    EquipmentService.getEquipmentById(equipmentId),
                    loanDate,
                    calculatedReturnDate,
                    true
            );

            MemberService.addToActiveLoans(member);
            LoanDB.addLoanToDB(loan);
        } catch (MaximumLoanLimitException | NotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }

    }
}
