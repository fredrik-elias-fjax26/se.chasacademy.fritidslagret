package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.EquipmentHandler;

public class SearchEquipment {
    protected static void searchEquipment() {
        IO.println("\nVilken inventarie vill du söka efter?:");
        String srcStr = IO.readln("(namn)>");
        IO.println("");
        int confirmationFound = 0;
        for (int i = 0; i < EquipmentHandler.getSizeOfInventory(); i++) {
            if (EquipmentHandler.getAllEquipment().get(i).getName().equals(srcStr)) {
                IO.println(EquipmentHandler.getAllEquipment().get(i));
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
