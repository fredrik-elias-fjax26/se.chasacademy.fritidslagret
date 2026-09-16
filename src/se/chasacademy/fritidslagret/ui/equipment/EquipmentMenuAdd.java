package se.chasacademy.fritidslagret.ui.equipment;

import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.model.equipment.EquipmentTypes;

public class EquipmentMenuAdd {
    static EquipmentService equipmentService =  new EquipmentService();
    private EquipmentMenuAdd () {

    }
    public static void addLoan() {}
    protected static void addEquipment() {
        IO.println("\nNu lägger vi till inventarie:");
        for (int i = 0; i < EquipmentTypes.values().length; i++) {
            IO.println("\t" + (i + 1) +". " + EquipmentTypes.values()[i].getDisplayName());
        }
        EquipmentTypes type;
        while (true) {
            try {
                int typeid = Integer.parseInt(IO.readln("Vilken typ ska den ha(Välj siffra): ")) - 1;
                type = EquipmentTypes.values()[typeid];
                break;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                IO.println("Felaktigt val!");
            }
        }
        String name = IO.readln("Vilket namn ska den ha: ");
        int maxLoanDays = 0;
        while (true) {
            try {
                maxLoanDays = Integer.parseInt(IO.readln("Hur många dagar kan man max låna i sträck?: "));
                break;
            } catch (NumberFormatException e) {
                IO.println("Antal dagar måste vara en siffra!");
            }
        }

        boolean available = true;

        equipmentService.addEquipment(equipmentService.getUniqueEquipmentId(), name, available, maxLoanDays, type);
    }
}
