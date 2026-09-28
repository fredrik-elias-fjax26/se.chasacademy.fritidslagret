package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.*;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.loan.LoanPolicy;
import se.chasacademy.fritidslagret.model.loan.LoanRules;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.repository.LoanRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LoanService {
    private final Clock clock;
    private final LoanRepository loanRepository;
    private final MemberService memberService;
    private final EquipmentService equipmentService;
    private final WaitingListService waitingListService;
    private final LoanRules loanRules;
    private final LoanPolicy loanPolicy;

    public int getActiveLoanCount() {
        return loanRepository.getLoanDatabase().size();
    }

    public Equipment getMostBorrowedEquipment() {
        Map<Equipment, Integer> loanCounts = new HashMap<>();

        for (Loan loan : loanRepository.getLoanDatabase().values()) {
            Equipment equipment = loan.getEquipment();
            loanCounts.merge(equipment, 1, Integer::sum);
        }

        Equipment mostBorrowed = null;
        int maxCount = 0;
        for (Map.Entry<Equipment, Integer> entry : loanCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostBorrowed = entry.getKey();
            }
        }

        return mostBorrowed;
    }

    public LoanService(LoanRepository loanRepository, MemberService memberService, EquipmentService equipmentService, WaitingListService waitingListService, Clock clock) {
        this.clock = clock;
        this.memberService = memberService;
        this.equipmentService = equipmentService;
        this.waitingListService = waitingListService;
        this.loanRepository = loanRepository;
        loanPolicy = new LoanPolicy(this);
        loanRules = new LoanRules(memberService, equipmentService);
    }

    public Loan startLoan(int equipmentId, Member member)
            throws EquipmentNotAvailableException, LoanMaximumLimitException, UserRevokedException, UserHasDelayedLoansException {

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

    public Loan returnLoan(Loan loan) {
        if (!loan.isLoanActive()) {
            throw new LoanAlreadyReturned();
        }
        Equipment returnedEquipment = loan.getEquipment();

        loan.setLoanActive(false);
        memberService.removeFromActiveLoans(loan.getMember());
        returnedEquipment.setAvailable(true);

        if (waitingListService.isMembersInQueueForEquipment(returnedEquipment)) {
            int equipmentId = loan.getEquipment().getEquipmentId();
            Loan newLoan;
            while (returnedEquipment.isAvailable() && waitingListService.isMembersInQueueForEquipment(returnedEquipment)) {
                Member nextMemberInQueue = waitingListService.getFromWaitingList(returnedEquipment);
                try {
                    newLoan = startLoan(equipmentId, nextMemberInQueue);
                } catch (UserRevokedException | LoanMaximumLimitException e) {
                    //Don't do anything if thrown as we are iterating through members in queue until a member can loan
                    continue;
                }
                return newLoan;
            }
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

    public Member putMemberInQueueForEquipment(Member member, Equipment equipment) {
        return waitingListService.addToWaitingList(equipment, member);
    }

    public int getDelayedDays(Loan loan) {
        try {
            LocalDate runDate = LocalDate.now(clock);
            return (int) ChronoUnit.DAYS.between(loan.getDateOfReturn(), runDate);
        } catch (NullPointerException e) {
            throw new LoanDoesNotExistException();
        }
    }

    public String getStatusOfLoan(Loan loan) {
        try {
            if (loan.isLoanActive()) {
                if (isDelayed(loan)) {
                    String delayedString;

                    int delayedDays = getDelayedDays(loan);

                    if (delayedDays == 1) {
                        delayedString = "dag";
                    } else {
                        delayedString = "dagar";
                    }

                    return String.format("Försenat med %d %s", delayedDays, delayedString);
                }
                return "Aktivt";

            }
            return "Avslutat";
        } catch (NullPointerException e) {
            throw new LoanDoesNotExistException();
        }
    }

    public Loan searchLoanByIdString(String searchIdTerm) {
        try {
            Loan requestedLoan = loanRepository.getLoanDatabase().get(Integer.parseInt(searchIdTerm));
            if (requestedLoan == null) {
                throw new LoanDoesNotExistException();
            }
            return requestedLoan;
        } catch (NumberFormatException e) {
            throw new LoanIdMustBeAnNumberException();
        }
    }

    public Map<Member, List<Loan>> searchLoansByMemberName(String memberName) {
        Map<Member, List<Loan>> loansForMembersByName = new HashMap<>();
        List<Loan> loans;
        for (Loan loan : loanRepository.getLoanDatabase().values()) {
            if (loan.getMember().getName().contains(memberName)) {
                if (!loansForMembersByName.containsKey(loan.getMember())) {
                    loans = new ArrayList<>();
                    loans.add(loan);
                    loansForMembersByName.put(loan.getMember(), loans);
                } else {
                    loansForMembersByName.get(loan.getMember()).add(loan);
                }
            }
        }
        if (loansForMembersByName.isEmpty()) {
            throw new NoLoanExistsForMemberSearchException();
        }
        return loansForMembersByName;
    }

    public List<Loan> searchLoansByMemberIdString(String memberId) {
        try {
            List<Loan> loans = new ArrayList<>();
            Member member = memberService.getMember(Integer.parseInt(memberId));

            if (member == null) {
                throw new MemberIsNullException();
            }

            for (Loan loan : loanRepository.getLoanDatabase().values()) {
                if (loan.getMember().getName().equals(memberId)) {
                    loans.add(loan);
                }
            }
            return loans;

        } catch (NumberFormatException e) {
            throw new MemberIdMustBeANumberException();
        }
    }

    public boolean memberHasDelayedLoansById(int memberId) {
        List<Loan> loans = getDelayedLoansSortedByDate();
        for (Loan loan : loans) {
            if (loan.getMember().getMemberId() == memberId ) {
                return true;
            }
        }
        return false;
    }
}
