package se.chasacademy.fritidslagret.ui;

// Imports
import se.chasacademy.fritidslagret.db.InventoryHandler;
import se.chasacademy.fritidslagret.model.EquipmentTypes;
import se.chasacademy.fritidslagret.model.Bike;
import se.chasacademy.fritidslagret.model.Tent;
import se.chasacademy.fritidslagret.model.SkiSet;

// Menu
public class Menu {
    //Main-Menu-loop
    public static void mainMenu() {
        InventoryHandler.addEquipment(new Bike("Bicycle", true, 30, "Bike"));
        InventoryHandler.addEquipment(new Tent("8-people-tent", false, 90, "Tent"));
        InventoryHandler.addEquipment(new SkiSet("SkiSet-for-Children", true, 3, "SkiSet"));

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

    // Methods for main-menu
    private static void addEquipment() {
        IO.println("\nNu lägger vi till inventarie:");
        for (int i = 0; i < EquipmentTypes.values().length; i++) {
            IO.println("\t" + (i + 1) +". " + EquipmentTypes.values()[i].getDisplayName());
        }
        int typeid = Integer.parseInt(IO.readln("Vilken typ ska den ha(Välj siffra): ")) - 1;
        EquipmentTypes type = EquipmentTypes.values()[typeid];
        String name = IO.readln("Vilket namn ska den ha: ");
        int maxLoanDays = Integer.parseInt(IO.readln("Hur många dagar kan man max låna i sträck?: "));
        boolean available = true;
        InventoryHandler.addEquipment(switch(type) {
            case BIKE -> new Bike(name, available, maxLoanDays, type.getDisplayName());
            case TENT -> new Tent(name, available, maxLoanDays, type.getDisplayName());
            case SKISET -> new SkiSet(name, available, maxLoanDays, type.getDisplayName());
        });
    }

    public static void listEquipment() {
        IO.println("");
        for (int i = 0; i < InventoryHandler.getIdCounter(); i++) {
            IO.println(InventoryHandler.getAllEquipments()[i]);
        }
        IO.println("");
    }

    public static void searchEquipment() {
        IO.println("\nVilken inventarie vill du söka efter?:");
        String srcStr = IO.readln("(namn)>");
        IO.println("");
        int confirmationFound = 0;
        for (int i = 0; i < InventoryHandler.getIdCounter(); i++) {
            if (InventoryHandler.getAllEquipments()[i].getName().equals(srcStr)) {
                IO.println(InventoryHandler.getAllEquipments()[i]);
                confirmationFound++;
            }
        }
        if (confirmationFound == 0) {
            IO.println(String.format("Ingen inventarie med namnet %s hittades!!", srcStr));
            IO.println("");
        } else {
            IO.println("");
        }


    }
}
