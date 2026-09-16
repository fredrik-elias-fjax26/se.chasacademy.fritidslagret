package se.chasacademy.fritidslagret.ui;

import static se.chasacademy.fritidslagret.ui.LoanAdd.addLoan;
import static se.chasacademy.fritidslagret.ui.LoanDelete.deleteLoan;
import static se.chasacademy.fritidslagret.ui.LoanList.listLoans;
import static se.chasacademy.fritidslagret.ui.LoanSearch.searchLoanById;
import static se.chasacademy.fritidslagret.ui.LoanSearch.searchLoansByName;

public class LoanMenu {
    // EquipmentMenu
        public static void loanMenu() {
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
                        case 1 -> addLoan();
                        case 2 -> listLoans();
                        case 3 -> searchLoanById();
                        case 4 -> searchLoansByName();
                        case 5 -> deleteLoan();
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
