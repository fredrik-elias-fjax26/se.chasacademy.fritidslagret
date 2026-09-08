package se.chasacademy.fritidslagret.ui;

// Imports
import se.chasacademy.fritidslagret.model.InventoryHandler;

import static se.chasacademy.fritidslagret.ui.AddEquipment.addEquipment;
import static se.chasacademy.fritidslagret.ui.ListEquipment.listEquipment;
import static se.chasacademy.fritidslagret.ui.SearchEquipment.searchEquipment;

// Menu
public class Menu {
    //Main-Menu-loop
    public static void mainMenu() {
        while (true) {
            // Main-menu
            IO.println("Välkommen till Fritidslagret\n-----------------");
            IO.println("""
                    1. Lägg till utrustning
                    2. Lista utrusning
                    3. Sök utrustning
                    4. Avsluta""");

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
        }
    }
}
