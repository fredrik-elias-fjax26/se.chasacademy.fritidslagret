// Pre-settings //

// Creation of inventory //
public Equipment[] equipmentDatabase = new Equipment[500]; // Create array with max of 500 inserts


// MAIN //
void main() {
    // Equipment base array - startup
    addEquipment("Mountain Bike", true);
    addEquipment("Family Tent", false);
    addEquipment("Ski Set", true);

    //Program-loop
    while(true) {
        // Main-menu
        IO.println("Välkommen till Fritidslagret\n-----------------");
        IO.println("""
                1. Lägg till utrustning
                2. Lista utrusning
                3. Sök utrustning
                4. Avsluta""");

        // Input main-menu-choice
        int choice = Integer.parseInt(IO.readln(">"));

        // Main-menu-switch
        switch(choice) {
            case 1 -> addEquipment();
            case 2 -> listEquipment();
            case 3 -> searchEquipment();
            case 4 ->  {
                return;
            }
        }
    }

}

// Methods for main-menu
private void addEquipment() {
    IO.println("\nNu lägger vi till inventarie:");
    String name = IO.readln("Vilket namn ska den ha: ");
    boolean available = true;
    equipmentDatabase[Equipment.idCounter] = new Equipment(name, available);
}

private void addEquipment(String name, boolean available) {
    equipmentDatabase[Equipment.idCounter] = new Equipment(name, available);
}

private void listEquipment() {
    IO.println("");
    for (int i = 0; i < Equipment.idCounter; i++) {
        IO.println(equipmentDatabase[i]);
    }
    IO.println("");
}

private void searchEquipment() {
    IO.println("\nVilken inventarie vill du söka efter?:");
    String srcStr = IO.readln("(namn)>");
    IO.println("");
    int confirmationFound = 0;
    for (int i = 1; i < Equipment.idCounter; i++) {
        if (equipmentDatabase[i].getName().equals(srcStr)) {
            IO.println(equipmentDatabase[i]);
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