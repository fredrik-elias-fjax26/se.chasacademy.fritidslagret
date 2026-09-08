package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.InventoryHandler;

public class ListEquipment {
    public static void listEquipment() {
        IO.println("");
        for (int i = 0; i < InventoryHandler.getIdCounter(); i++) {
            IO.println(InventoryHandler.getAllEquipments()[i]);
        }
        IO.println("");
    }
}
