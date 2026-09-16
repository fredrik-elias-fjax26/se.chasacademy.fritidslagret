package se.chasacademy.fritidslagret.ui.member;

public class MemberMenu {
    public static void memberMenu() {
        while (true) {
            // Main-menu
            IO.println("Medlemskap\n-----------------");
            IO.println("""
                Blank menu än så länge
                6. Tillbaka""");
            // Input main-menu-choice
            int choice = Integer.parseInt(IO.readln(">"));

            // Main-menu-switch
            switch (choice) {
                case 6 -> {
                    return;
                }
            }
        }
    }
}
