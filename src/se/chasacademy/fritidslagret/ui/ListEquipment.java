package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.EquipmentHandler;

public class ListEquipment {
    public static void listEquipment() {
        IO.println("");
        for (int i = 0; i < EquipmentHandler.getAllEquipment().size(); i++) {
            IO.println(EquipmentHandler.getAllEquipment().get(i));
        }
        IO.println("");
    }
}
