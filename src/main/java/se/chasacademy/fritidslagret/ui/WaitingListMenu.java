package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.WaitingListService;

public class WaitingListMenu {
    private final WaitingListMenuList waitingListMenuList;

    public WaitingListMenu(WaitingListService waitingListService) {
        this.waitingListMenuList = new WaitingListMenuList(waitingListService);
    }

    public void waitingListMenu() {
        while (true) {
            try {
                // Väntelistor-menu
                IO.println("Väntelistor\n-----------------");
                IO.println("""
                1. Lista alla Artiklar med kö
                2. Tillbaka""");
                // Input main-menu-choice

                int choice = Integer.parseInt(IO.readln(">"));

                // Väntelistor-menu-switch
                switch (choice) {
                    case 1 -> waitingListMenuList.listAllEquipmentWithQueue();
                    case 2 -> {
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                IO.println("Menyvalet måste vara en siffra");
            }
        }
    }
}
