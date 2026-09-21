package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

public class MainMenu {
    private final LoanMenu loanMenu;
    private final EquipmentMenu equipmentMenu;
    private final MemberMenu memberMenu;

    public MainMenu(LoanService loanService, MemberService memberService, EquipmentService equipmentService) {
        this.equipmentMenu = new EquipmentMenu(equipmentService);
        this.memberMenu = new MemberMenu(memberService);
        this.loanMenu = new LoanMenu(loanService);
    }

    public void mainMenu() {
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
                    case 1 -> equipmentMenu.equipmentMenu();
                    case 2 -> memberMenu.memberMenu();
                    case 3 -> loanMenu.loanMenu();
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
