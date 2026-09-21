package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.EquipmentService;

// EquipmentMenu
public class EquipmentMenu {
    private final EquipmentMenuAdd equipmentMenuAdd;
    private final EquipmentMenuSearch equipmentMenuSearch;
    private final EquipmentMenuList equipmentMenuList;

    public EquipmentMenu(EquipmentService equipmentService) {
        this.equipmentMenuAdd = new EquipmentMenuAdd(equipmentService);
        this.equipmentMenuSearch = new EquipmentMenuSearch(equipmentService);
        this.equipmentMenuList = new EquipmentMenuList(equipmentService);
    }
    //Main-EquipmentMenu-loop
    protected void equipmentMenu() {
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
                    case 1 -> equipmentMenuAdd.addEquipment();
                    case 2 -> equipmentMenuList.listEquipment();
                    case 3 -> equipmentMenuSearch.searchEquipment();
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
