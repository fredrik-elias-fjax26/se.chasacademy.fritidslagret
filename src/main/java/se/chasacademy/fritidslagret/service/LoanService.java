package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.EquipmentNotAvailableException;
import se.chasacademy.fritidslagret.exception.LoanAlreadyReturned;
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
import java.util.*;

public class LoanService {
    private final Clock clock;
    private final LoanRepository loanRepository;
    private final MemberService memberService;
    private final EquipmentService equipmentService;
    private final WaitingListService waitingListService;
    private final LoanRules loanRules;
    private final LoanPolicy loanPolicy;

    public LoanService(LoanRepository loanRepository ,MemberService memberService, EquipmentService equipmentService, WaitingListService waitingListService, Clock clock) {
        this.clock = clock;
        this.memberService = memberService;
        this.equipmentService = equipmentService;
        this.waitingListService = waitingListService;
        this.loanRepository = loanRepository;
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
                equipmentForLoan,
                loanDate,
                calculatedReturnDate,
                true
        );

        memberService.addToActiveLoans(member);
        loanRepository.addLoan(loan);
        equipmentForLoan.setAvailable(false);
        return loan;
    }

    public Member returnLoan(Loan loan) {
        Equipment returnedEquipment = loan.getEquipment();

        loan.setLoanActive(false);
        memberService.removeFromActiveLoans(loan.getMember());
        returnedEquipment.setAvailable(true);

        if (waitingListService.isMembersInQueueForEquipment(returnedEquipment)) {
            return waitingListService.getFromWaitingList(returnedEquipment);
        }

        return null;
    }

    public Loan getLoanById(int id) {
        return loanRepository.getLoanDatabase().get(id);
    }

    public List<Loan> getDelayedLoansSortedByDate() {
        return loanRepository.getLoanDatabase().values().stream()
                .filter(this::isDelayed)
                .sorted(Comparator.comparing(Loan::getDateOfReturn))
                .toList();
        }

    public SortedMap<Integer, Loan> getAllActiveLoansSortedById() {
        SortedMap<Integer, Loan> allActiveLoans = new TreeMap<>();
        for (Loan loan : loanRepository.getLoanDatabase().values()) {
            if (loan.isLoanActive()) {
                allActiveLoans.put(loan.getLoanId(), loan);
            }
        }
        return allActiveLoans;
    }

    public boolean isDelayed(Loan loan){
        if (loan.isLoanActive()) {
            return loan.getDateOfReturn().isBefore(LocalDate.now(clock));
        }
        return false;
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
