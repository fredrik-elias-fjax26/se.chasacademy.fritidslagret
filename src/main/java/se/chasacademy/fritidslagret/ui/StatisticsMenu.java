package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.StatisticsService;

public class StatisticsMenu {
    private final StatisticsService statisticsService;
    public StatisticsMenu(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }



    protected void statisticsMenu() {
        while (true) {
                IO.println("Statistik\n-----------------");
                IO.println("""
                        1. Antal aktiva lån
                        2. Antal medlemmar
                        3. Mest lånade utrustning
                        4. Antal tillgängliga utrustningsobjekt
                        5. Tillbaka
                        """);

                int choice = Integer.parseInt(IO.readln(">"));

                switch (choice) {
                    case 1 -> statisticsService.showActiveLoanCount();
                    case 2 -> statisticsService.showMemberCount();
                    case 3 -> statisticsService.showMostBorrowedEquipment();
                    case 4 -> statisticsService.showAvailableEquipmentCount();
                    case 5 -> {
                        return;
                    }
                }

            }
        }
    }
