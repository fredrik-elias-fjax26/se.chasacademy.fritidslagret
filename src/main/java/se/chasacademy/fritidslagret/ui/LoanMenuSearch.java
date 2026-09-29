package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.*;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.service.LoanService;

import java.util.List;
import java.util.Map;


public class LoanMenuSearch {
    private final LoanService loanService;

    LoanMenuSearch(LoanService loanService) {
        this.loanService = loanService;
    }

    void searchLoanByLoanId() {
        IO.println("Sök lån efter lån-ID.\n");
        while (true) {
            String searchIdString = IO.readln("Låne-Id: ");
            try {
                Loan loan = loanService.searchLoanByIdString(searchIdString);
                Member member = loan.getMember();
                IO.println(loan + "\n\t- Lånad av: " + member + "\n\t\t" + "Lånet är: " + loanService.getStatusOfLoan(loan) + "\n");
                IO.println("");
                return;
            } catch (LoanIdMustBeAnNumberException | LoanDoesNotExistException e) {
                IO.println(e.getMessage());
            }
        }
    }

    void searchLoansByMemberName() {
        IO.println("Sök lån efter medlemsnamn");
        while (true) {
            String searchNameString = IO.readln("Medlems-namn: ").toLowerCase();
            try {
                Map<Member, List<Loan>> searchResultMap = loanService.searchLoansByMemberName(searchNameString);
                for (Member memberWithLoans : searchResultMap.keySet()) {
                    IO.println("\n" + "*".repeat(memberWithLoans.toString().length()));
                    IO.println(memberWithLoans);
                    IO.println("*".repeat(memberWithLoans.toString().length()) + "\n");
                    for (Loan loan : searchResultMap.get(memberWithLoans)) {
                        IO.println(loan + "\n\t - Lånet är: " + loanService.getStatusOfLoan(loan));
                    }
                }
                IO.println("");
                return;

            } catch (NoLoanExistsForMemberSearchException | SearchStringMustBeAtleastTwoCharactersLongException e) {
                IO.println(e.getMessage());
            }
        }
    }

    void searchLoansByMemberId() {
        IO.println("Sök lån genom Medlems-Id\n");
        while (true) {
            try {
                String searchIdString = IO.readln("Medlems-Id: ");
                List<Loan> loans = loanService.searchLoansByMemberIdString(searchIdString);
                for (Loan loan : loans) {
                    IO.println(loan + "\n\t - Lånet är: " + loanService.getStatusOfLoan(loan));
                }
                IO.println("");
                return;

            } catch (MemberIdMustBeANumberException e) {
                IO.println(e.getMessage());
            }
        }
    }
}
