package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.EquipmentService;
import se.chasacademy.fritidslagret.model.EquipmentTypes;

public class EquipmentMenuAdd {
    protected static void addEquipment() {
        IO.println("\nNu lägger vi till inventarie:");
        for (int i = 0; i < EquipmentTypes.values().length; i++) {
            IO.println("\t" + (i + 1) +". " + EquipmentTypes.values()[i].getDisplayName());
        }
        int typeid = Integer.parseInt(IO.readln("Vilken typ ska den ha(Välj siffra): ")) - 1;
        EquipmentTypes type = EquipmentTypes.values()[typeid];
        String name = IO.readln("Vilket namn ska den ha: ");
        int maxLoanDays = Integer.parseInt(IO.readln("Hur många dagar kan man max låna i sträck?: "));
        boolean available = true;

        EquipmentService.addEquipment(EquipmentService.getUniqueEquipmentId(), name, available, maxLoanDays, type);
    }
}
