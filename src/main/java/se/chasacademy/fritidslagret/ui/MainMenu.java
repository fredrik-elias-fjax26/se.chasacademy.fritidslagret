package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;
import se.chasacademy.fritidslagret.service.ReportService;
import se.chasacademy.fritidslagret.service.WaitingListService;
import se.chasacademy.fritidslagret.service.StatisticsService;

public class MainMenu {
    private final LoanMenu loanMenu;
    private final EquipmentMenu equipmentMenu;
    private final MemberMenu memberMenu;
    private final StatisticsMenu statisticsMenu;
    private final WaitingListMenu waitingListMenu;
    private final ReportMenu reportMenu;


    public MainMenu(LoanService loanService, MemberService memberService, EquipmentService equipmentService, StatisticsService statisticsService, WaitingListService waitingListService, ReportService reportService) {
        this.equipmentMenu = new EquipmentMenu(equipmentService);
        this.memberMenu = new MemberMenu(memberService);
        this.loanMenu = new LoanMenu(loanService, equipmentService, memberService);
        this.statisticsMenu = new StatisticsMenu(statisticsService);
        this.waitingListMenu = new WaitingListMenu(waitingListService);
        this.reportMenu = new ReportMenu(reportService);

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
                        4. Statistik
                        5. Väntelistor
                        6. Rapporter
                        7. Avsluta
                        """);

                // Input main-menu-choice
                int choice = Integer.parseInt(IO.readln(">"));

                // Main-menu-switch
                switch (choice) {
                    case 1 -> equipmentMenu.equipmentMenu();
                    case 2 -> memberMenu.memberMenu();
                    case 3 -> loanMenu.loanMenu();
                    case 4 -> statisticsMenu.statisticsMenu();
                    case 5 -> waitingListMenu.waitingListMenu();
                    case 6 -> reportMenu.showReport();
                    case 7 -> {
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                IO.println("Menyvalet måste vara en siffra");
            }
        }
    }
}
