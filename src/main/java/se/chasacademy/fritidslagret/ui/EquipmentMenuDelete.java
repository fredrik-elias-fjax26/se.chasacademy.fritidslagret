package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.ConfirmationFailedException;
import se.chasacademy.fritidslagret.exception.EquipmentIdMustBeANumberException;
import se.chasacademy.fritidslagret.exception.EquipmentIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.service.EquipmentService;

import java.util.Map;

public class EquipmentMenuDelete {
    private final EquipmentService equipmentService;

    public EquipmentMenuDelete(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    public void deleteEquipment() {
        IO.println("Ta bort en artikel...");
        IO.println("\n(Använd sökfunktionen för att hitta rätt Artikel-Id)\n");
        Map<Integer, Equipment> equipmentList = equipmentService.getAllEquipment();
        try {
            int equipmentIdToDelete = Integer.parseInt(IO.readln("Vilket artikel-id vill du radera?: "));

            String confirmationName = IO.readln("Skriv hela EXAKTA namnet på artikeln för att bekräfta radering:\nDETTA GÅR EJ ATT ÅNGRA!!\n->");
            String equipmentName = equipmentList.get(equipmentIdToDelete).getEquipmentName();

            if (!equipmentName.equals(confirmationName)) {
                throw new ConfirmationFailedException();
            }

            equipmentService.deleteEquipment(equipmentList.get(equipmentIdToDelete));
            IO.println(String.format("Artikel med id: %d har raderats", equipmentIdToDelete));

        } catch (EquipmentIsNullException | EquipmentIdMustBeANumberException | ConfirmationFailedException e) {
            IO.println(e.getMessage());
        }

    }
}
