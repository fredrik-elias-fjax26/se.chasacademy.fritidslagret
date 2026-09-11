// Pre-settings //
import se.chasacademy.fritidslagret.db.Startup;
import se.chasacademy.fritidslagret.model.MemberHandler;
import se.chasacademy.fritidslagret.ui.Menu;
import se.chasacademy.fritidslagret.model.Member;

// MAIN //
void main() {
    Startup.startUp();
    //Menu.mainMenu();

    /* Print Membernames
    Map<Integer, Member> membersDB = MemberHandler.getMembers();
    for (Integer id : membersDB.keySet()) {
        String name = membersDB.get(id).getName();
        IO.println("Member: " + name);
    }*/



}



