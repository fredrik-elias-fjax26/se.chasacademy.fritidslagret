package se.chasacademy.fritidslagret.db;

import se.chasacademy.fritidslagret.model.Bike;
import se.chasacademy.fritidslagret.model.EquipmentTypes;
import se.chasacademy.fritidslagret.model.InventoryHandler;

public class Startup {
    public void Startup() {
        InventoryHandler.addEquipment(EquipmentTypes.BIKE, "Bicycle", true, 30);
        InventoryHandler.addEquipment(EquipmentTypes.TENT, "8-people-tent", false, 90);
        InventoryHandler.addEquipment(EquipmentTypes.SKI_SET, "SkiSet-for-Children", true, 3);
    }
}
