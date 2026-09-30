package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.model.member.MemberStatus;
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
                    1. Se medlemmar
                    2. Redigera medlemsstatus
                    6. Tillbaka""");
            // Input medlems-menu-choice
            int choice = Integer.parseInt(IO.readln(">"));

            // Medlems-menu-switch
            switch (choice) {
                case 1 -> {
                    for (var member :memberService.getMembers().values()){
                        IO.println(member.getMemberId() + ":" + member + "-"
                                + member.getMemberStatus().getFriendlyName());

                    }
                }
                case 2 -> {
                    int id = Integer.parseInt(IO.readln("Medlems-ID "));
                    MemberStatus[] statuses = MemberStatus.values();
                    for (int i = 0; i < statuses.length; i++){
                        IO.println((i + 1) + ". " + statuses[i].getFriendlyName());
                    }
                    int pick = Integer.parseInt(IO.readln("Välj ny status"));
                    if (pick < 1 || pick > statuses.length){
                        IO.println("Ogiltigt val.");
                    }
                    else if (memberService.changeMemberStatus(id, statuses[pick -1])){
                        IO.println("Status ändrad.");
                    }
                    else {
                        IO.println("Ingen medlem med ID" + id);
                    }

                }

                case 6 -> {
                    return;
                }
            }
        }
    }
}
