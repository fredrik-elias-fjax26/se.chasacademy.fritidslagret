package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.MaximumLoanLimitException;
import se.chasacademy.fritidslagret.Exceptions.NotAvailableException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanHandler {

    public void startLoan(int id, Member member) throws NotAvailableException, MaximumLoanLimitException {
        if (member.getActiveLoans() >= 3)
            throw new MaximumLoanLimitException("Maximalt antal lån redan uppnådd");
        else if (!EquipmentHandler.getEquipmentById(id).isAvailable()) {
            throw new NotAvailableException("Produkten inte tillgänglig för utlåning");
        }

        Equipment equipmentForLoan = EquipmentHandler.getEquipmentById(id);

        LocalDate calculatedReturnDate = LocalDate.now().plusDays(equipmentForLoan.getMaxLoanDays());

        //TODO Här är vi!!!

    }
}
