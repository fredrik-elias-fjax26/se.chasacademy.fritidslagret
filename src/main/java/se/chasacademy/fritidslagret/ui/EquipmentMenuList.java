package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.EquipmentService;

import java.util.Map;

public class EquipmentMenuList {
    private final EquipmentService equipmentService;

    public EquipmentMenuList(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    protected void listEquipment() {
        IO.println("");
        for (Map.Entry<Integer, Equipment> entry : equipmentService.getAllEquipment().entrySet()) {
             IO.println(entry.getValue());
        }
        IO.println("");
    }
}
