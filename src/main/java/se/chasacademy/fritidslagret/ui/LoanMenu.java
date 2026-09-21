package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.LoanService;

public class LoanMenu {
    LoanMenuList loanMenuList;
    LoanMenuAdd loanMenuAdd;
    LoanMenuSearch loanMenuSearch;
    LoanMenuDelete loanMenuDelete;
    LoanService loanService;

    protected LoanMenu(LoanService loanService) {
        this.loanMenuList = new LoanMenuList(loanService);
        this.loanMenuAdd = new LoanMenuAdd(loanService);
        this.loanMenuSearch = new LoanMenuSearch(loanService);
        this.loanMenuDelete = new LoanMenuDelete(loanService);
        this.loanService = loanService;
    }
    // EquipmentMenu
        protected void loanMenu() {
            while (true) {
                try {
                    // Main-menu
                    IO.println("Lånealternativ\n-----------------");
                    IO.println("""
                        1. Lägg till Lån
                        2. Lista Aktiva Lån
                        3. Lista Försenade Lån
                        4. Sök Lån
                        5. Återlämna Lån
                        6. Tillbaka""");

                    // Input main-menu-choice
                    int choice = Integer.parseInt(IO.readln(">"));

                    // Main-menu-switch
                    switch (choice) {
                        case 1 -> loanMenuAdd.addLoan();
                        case 2 -> loanMenuList.listLoans();
                        case 3 -> loanMenuSearch.searchLoanById();
                        case 4 -> loanMenuSearch.searchLoansByName();
                        case 5 -> loanMenuDelete.deleteLoan();
                        case 6 -> {
                            return;
                        }
                    }
                } catch (NumberFormatException e) {
                    IO.println("Menynvalet måste vara en siffra");
                }
            }
        }
    }
