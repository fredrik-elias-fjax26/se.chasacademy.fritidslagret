// Pre-settings //
import se.chasacademy.fritidslagret.Startup;
import se.chasacademy.fritidslagret.ui.EquipmentMenu;
import se.chasacademy.fritidslagret.ui.LoanMenu;
import se.chasacademy.fritidslagret.ui.MemberMenu;

// MAIN //
void main() {
    // Startup
    Startup.startUp();
    //Main-Menu-loop
        while (true) {
            // Main-menu
            IO.println("Välkommen till Fritidslagret\n-----------------");
            IO.println("""
                    1. Utrustning
                    2. Medlemmar
                    3. Lån
                    4. Avsluta""");

            // Input main-menu-choice
            int choice = Integer.parseInt(IO.readln(">"));

            // Main-menu-switch
            switch (choice) {
                case 1 -> EquipmentMenu.equipmentMenu();
                case 2 -> MemberMenu.memberMenu();
                case 3 -> LoanMenu.loanMenu();
                case 4 -> {
                    return;
                }
            }
        }
    }



