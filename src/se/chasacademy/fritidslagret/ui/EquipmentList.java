package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.EquipmentService;

public class EquipmentList {
    public static void listEquipment() {
        IO.println("");
        for (int i = 0; i < EquipmentService.getAllEquipment().size(); i++) {
            IO.println(EquipmentService.getAllEquipment().get(i));
        }
        IO.println("");
    }
}
