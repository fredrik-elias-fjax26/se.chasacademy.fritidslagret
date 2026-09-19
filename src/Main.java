// Pre-settings //
import se.chasacademy.fritidslagret.Startup;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;
import se.chasacademy.fritidslagret.ui.MainMenu;

// MAIN //
void main() {
    EquipmentRepository equipmentRepository = EquipmentRepository.getInstance();
    MemberService memberService = new MemberService();
    EquipmentService equipmentService = new EquipmentService(equipmentRepository);
    LoanService loanService = new LoanService(memberService, equipmentService, Clock.systemUTC());

    // Startup
    Startup startup = new Startup(equipmentService, memberService, loanService);
    startup.startUp();
    //Main-Menu-loop
    MainMenu mainMenu = new MainMenu(loanService, memberService, equipmentService);
    mainMenu.mainMenu();
}



