package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

public class LoanMenu {
    LoanMenuList loanMenuList;
    LoanMenuAdd loanMenuAdd;
    LoanMenuSearch loanMenuSearch;
    LoanMenuReturn loanMenuReturn;
    LoanService loanService;

     LoanMenu(LoanService loanService, EquipmentService equipmentService, MemberService memberService) {
        this.loanMenuList = new LoanMenuList(loanService);
        this.loanMenuAdd = new LoanMenuAdd(loanService, equipmentService, memberService);
        this.loanMenuSearch = new LoanMenuSearch(loanService);
        this.loanMenuReturn = new LoanMenuReturn(loanService);
        this.loanService = loanService;
    }
    // EquipmentMenu
         void loanMenu() {
            while (true) {
                try {
                    // Main-menu
                    IO.println("Lånealternativ\n-----------------");
                    IO.println("""
                        1. Lägg till Lån
                        2. Lista Aktiva Lån
                        3. Lista Försenade Lån
                        4. Sök Lån efter Låne-Id
                        5. Sök Lån efter Medlemsnamn
                        6. Återlämna Lån
                        7. Tillbaka""");

                    // Input main-menu-choice
                    int choice = Integer.parseInt(IO.readln(">"));

                    // Main-menu-switch
                    switch (choice) {
                        case 1 -> loanMenuAdd.addLoan();
                        case 2 -> loanMenuList.listLoans();
                        case 3 -> loanMenuList.listDelayedLoans();
                        case 4 -> loanMenuSearch.searchLoanByLoanId();
                        case 5 -> loanMenuSearch.searchLoansByMemberName();
                        case 6 -> loanMenuReturn.returnLoan();
                        case 7 -> {
                            return;
                        }
                    }
                } catch (NumberFormatException e) {
                    IO.println("Menynvalet måste vara en siffra");
                }
            }
        }
    }
