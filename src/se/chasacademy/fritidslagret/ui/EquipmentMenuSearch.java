package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.Equipment;
import se.chasacademy.fritidslagret.model.EquipmentService;

public class EquipmentMenuSearch {
    protected static void searchEquipment() {
        IO.println("\nVilken inventarie vill du söka efter?:");
        String srcStr = IO.readln("(namn)>");
        IO.println("");

        Equipment foundEquipment = EquipmentService.getEquipmentByName(srcStr);
        if (foundEquipment == null) {
            IO.println(String.format("Ingen inventarie med namnet %s hittades!!", srcStr));
            IO.println("");
        } else {
            IO.println(foundEquipment);
        }
    }
}
