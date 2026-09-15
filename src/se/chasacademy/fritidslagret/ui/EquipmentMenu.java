package se.chasacademy.fritidslagret.ui;

// Imports
import static se.chasacademy.fritidslagret.ui.EquipmentMenuAdd.addEquipment;
import static se.chasacademy.fritidslagret.ui.EquipmentList.listEquipment;
import static se.chasacademy.fritidslagret.ui.EquipmentMenuSearch.searchEquipment;

// EquipmentMenu
public class EquipmentMenu {
    //Main-EquipmentMenu-loop
    public static void equipmentMenu() {
        while (true) {
            try {
                // Main-menu
                IO.println("Utrustningsmeny\n-----------------");
                IO.println("""
                        1. Lägg till utrustning
                        2. Lista utrusning
                        3. Sök utrustning
                        4. Tillbaka""");

                // Input main-menu-choice
                int choice = Integer.parseInt(IO.readln(">"));

                // Main-menu-switch
                switch (choice) {
                    case 1 -> addEquipment();
                    case 2 -> listEquipment();
                    case 3 -> searchEquipment();
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
