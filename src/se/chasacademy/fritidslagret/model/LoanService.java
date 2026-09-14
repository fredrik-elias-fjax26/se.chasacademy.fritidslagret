package se.chasacademy.fritidslagret.model;

import se.chasacademy.fritidslagret.Exceptions.MaximumLoanLimitException;
import se.chasacademy.fritidslagret.Exceptions.NotAvailableException;

import java.time.LocalDate;

public class LoanService {

    public void startLoan(int id, Member member) throws NotAvailableException, MaximumLoanLimitException {
        if (member.getActiveLoans() >= 3)
            throw new MaximumLoanLimitException("Maximalt antal lån redan uppnådd");
        else if (!EquipmentService.getEquipmentById(id).isAvailable()) {
            throw new NotAvailableException("Produkten inte tillgänglig för utlåning");
        }

        Equipment equipmentForLoan = EquipmentService.getEquipmentById(id);

        LocalDate calculatedReturnDate = LocalDate.now().plusDays(equipmentForLoan.getMaxLoanDays());

        //TODO Här är vi!!!


    }
}
