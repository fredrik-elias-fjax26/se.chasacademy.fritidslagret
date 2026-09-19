package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.LoanMaximumLimitException;
import se.chasacademy.fritidslagret.exception.UserRevokedException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.loan.LoanPolicy;
import se.chasacademy.fritidslagret.model.loan.LoanRules;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.repository.LoanRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LoanService {
    private final Clock clock;
    private final LoanRepository loanRepository;
    private final MemberService memberService;
    private final EquipmentService equipmentService;
    private final LoanRules loanRules;
    private final LoanPolicy loanPolicy;

    public LoanService(MemberService memberService, EquipmentService equipmentService, Clock clock) {
        this.clock = clock;
        this.memberService = memberService;
        this.equipmentService = equipmentService;
        this.loanRepository = LoanRepository.getInstance();
        loanPolicy = new LoanPolicy();
        loanRules = new LoanRules(memberService, equipmentService);
    }

    public Loan startLoan(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException, UserRevokedException {

        loanPolicy.checkLoanPolicy(member);
        loanRules.checkLoanRules(equipmentId, member);


        Equipment equipmentForLoan = equipmentService.getEquipmentById(equipmentId);

        LocalDate calculatedReturnDate = LocalDate.now(clock).plusDays(equipmentForLoan.getMaxLoanDays());
        LocalDate loanDate = LocalDate.now(clock);

        Loan loan = new Loan(
                member,
                equipmentService.getEquipmentById(equipmentId),
                loanDate,
                calculatedReturnDate,
                true
        );

        memberService.addToActiveLoans(member);
        loanRepository.addLoan(loan);
        equipmentService.getEquipmentById(equipmentId).setAvailable(false);
        return loan;
    }

    public Loan getLoanById(int id) {
        return loanRepository.getLoanDatabase().get(id);
    }

    public boolean isDelayed(Loan loan){
        return loan.getDateOfReturn().isBefore(LocalDate.now(clock));
    }

    public Map<Integer, Loan> getExpiredLoans() {
        Map<Integer, Loan> expiredLoanList = new HashMap<>();

        for (Loan loan : loanRepository.getLoanDatabase().values()) {
            if (loan.getDateOfReturn().isBefore(LocalDate.now(clock) )){
                int id = loan.getLoanId();
                expiredLoanList.put(id , loan);
            }
        }
        return expiredLoanList;
    }

}
