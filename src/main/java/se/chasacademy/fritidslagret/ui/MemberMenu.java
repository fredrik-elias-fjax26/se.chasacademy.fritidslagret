package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.service.MemberService;

public class MemberMenu {
    private final MemberService memberService;
    
    public MemberMenu(MemberService memberService) {
        this.memberService = memberService;
    }

     void memberMenu() {
        while (true) {
            // Medlems-menu
            IO.println("Medlemskap\n-----------------");
            IO.println("""
                Blank menu än så länge
                6. Tillbaka""");
            // Input medlems-menu-choice
            int choice = Integer.parseInt(IO.readln(">"));

            // Medlems-menu-switch
            switch (choice) {
                case 6 -> {
                    return;
                }
            }
        }
    }
}
