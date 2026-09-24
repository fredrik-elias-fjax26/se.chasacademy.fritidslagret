package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.EquipmentService;

import java.util.Map;

public class EquipmentMenuSearch {
    private final EquipmentService equipmentService;

    public EquipmentMenuSearch(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    protected void searchEquipment() {
        IO.println("\nVilken inventarie vill du söka efter?:");
        String srcStr = IO.readln("(namn)>");

        Map<Integer, Equipment> foundEquipment = equipmentService.searchForEquipmentByName(srcStr);

        if (foundEquipment.isEmpty()) {
            IO.println("Inga artiklar hittades");
        } else {
            for (Equipment equipment : foundEquipment.values()) {
                IO.println(String.format("Artikel-ID: %s, Namn: %s, Utlånad: %s",
                        equipment.getEquipmentId(), equipment.getEquipmentName(),
                        String.valueOf(equipment.isAvailable()).replace("true", "Nej")
                                .replace("false", "Ja")));
            }
        }
    }
}
