package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.ui.equipment.EquipmentMenu;
import se.chasacademy.fritidslagret.ui.loan.LoanMenu;
import se.chasacademy.fritidslagret.ui.member.MemberMenu;

import java.awt.*;

public class MainMenu {
    public static void mainMenu() {
        while (true) {
            try {
                // Main-menu
                IO.println("Välkommen till Fritidslagret\n-----------------");
                IO.println("""
                        1. Utrustning
                        2. Medlemmar
                        3. Lån
                        4. Avsluta""");

                // Input main-menu-choice
                int choice = Integer.parseInt(IO.readln(">"));

                // Main-menu-switch
                switch (choice) {
                    case 1 -> EquipmentMenu.equipmentMenu();
                    case 2 -> MemberMenu.memberMenu();
                    case 3 -> LoanMenu.loanMenu();
                    case 4 -> {
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                IO.println("Menyvalet måste vara en siffra");
            }
        }
    }
}
