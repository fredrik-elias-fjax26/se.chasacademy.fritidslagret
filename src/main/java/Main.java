// Pre-settings //
import se.chasacademy.fritidslagret.Startup;
import se.chasacademy.fritidslagret.repository.EquipmentRepository;
import se.chasacademy.fritidslagret.repository.LoanRepository;
import se.chasacademy.fritidslagret.repository.MemberRepository;
import se.chasacademy.fritidslagret.repository.WaitingListRepository;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;
import se.chasacademy.fritidslagret.service.WaitingListService;
import se.chasacademy.fritidslagret.service.StatisticsService;
import se.chasacademy.fritidslagret.ui.MainMenu;


// MAIN //
void main() {
    EquipmentRepository equipmentRepository = EquipmentRepository.getInstance();
    MemberRepository memberRepository = MemberRepository.getInstance();
    LoanRepository loanRepository = LoanRepository.getInstance();
    WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
    Clock clock = Clock.systemUTC();

    MemberService memberService = new MemberService(memberRepository);
    EquipmentService equipmentService = new EquipmentService(equipmentRepository);
    WaitingListService waitingListService = new WaitingListService(waitingListRepository);

    LoanService loanService = new LoanService(loanRepository, memberService, equipmentService, waitingListService, clock);
    StatisticsService statisticsService = new StatisticsService(equipmentService, loanService, memberService);

    // Startup
    Startup startup = new Startup(equipmentService, memberService, loanService);
    startup.startUp();
    //fritidslagret.Main-Menu-loop
    MainMenu mainMenu = new MainMenu(loanService, memberService, equipmentService, statisticsService, waitingListService);
    mainMenu.mainMenu();
}



