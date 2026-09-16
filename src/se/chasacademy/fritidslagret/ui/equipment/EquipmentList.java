package se.chasacademy.fritidslagret.ui.equipment;

import se.chasacademy.fritidslagret.service.EquipmentService;

public class EquipmentList {
    public static void listEquipment() {
        IO.println("");
        for (int i = 0; i < EquipmentService.getAllEquipment().size(); i++) {
            IO.println(EquipmentService.getAllEquipment().get(i));
        }
        IO.println("");
    }
}
