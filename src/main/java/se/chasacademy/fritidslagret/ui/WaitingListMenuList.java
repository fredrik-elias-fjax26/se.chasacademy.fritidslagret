package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.WaitingListService;

import java.util.List;

public class WaitingListMenuList {
    private final WaitingListService waitingListService;

    public WaitingListMenuList(WaitingListService waitingListService) {
        this.waitingListService = waitingListService;
    }

    public void listAllEquipmentWithQueue() {
        List<Equipment> listOfQueuedEquipments = waitingListService.getAllEquipmentInWaitingList();
        if (listOfQueuedEquipments.isEmpty()) {
            IO.println("Det finns inga Artiklar med Kö!");
        } else {
            IO.println("Dessa Artiklar finns det kö på:");
            for (Equipment equipment : listOfQueuedEquipments) {
                IO.println(equipment.getEquipmentName());
            }
        }
    }
}
