package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.EquipmentService;

public class EquipmentMenuList {
    private final EquipmentService equipmentService;

    public EquipmentMenuList(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    protected void listEquipment() {
        IO.println("");
        for (int i = 0; i < equipmentService.getAllEquipment().size(); i++) {
            IO.println(equipmentService.getAllEquipment().get(i));
        }
        IO.println("");
    }
}
