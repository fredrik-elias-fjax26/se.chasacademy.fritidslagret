package se.chasacademy.fritidslagret.ui;

import se.chasacademy.fritidslagret.exception.*;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

public class LoanMenuAdd {
    private final LoanService loanService;
    private final EquipmentService equipmentService;
    private final MemberService memberService;

    LoanMenuAdd(LoanService loanService, EquipmentService equipmentService, MemberService memberService) {
        this.loanService = loanService;
        this.equipmentService = equipmentService;
        this.memberService = memberService;
    }

    protected void addLoan() {
        String stringToIntFailMsgForLoop = "Inte en giltig siffra, försök igen!";
        Member verifiedMember;
        int verifiedEquipmentId;

        try {
            IO.println("Lägg till Lån");
            while (true) {
                try {
                    int memberIdToCreateLoan = Integer.parseInt(IO.readln("Ange ditt Medlemsnummer: "));

                    verifiedMember = memberService.getMember(memberIdToCreateLoan);
                    if (verifiedMember == null) {
                        throw new MemberIsNullException();
                    }
                    break;

                } catch (NumberFormatException e) {
                    IO.println(stringToIntFailMsgForLoop);
                } catch (MemberIsNullException e) {
                    IO.println(e.getMessage());
                }
            }

            IO.println("\nVilken Artikel vill du låna?\n");

            while (true) {
                String requestedEquipmentId = IO.readln("Ange ArtikelId (Enter för lista): ");

                if (requestedEquipmentId.isEmpty()) {
                    for (Equipment equipment : equipmentService.getAllEquipmentsSortedById().values()) {
                       IO.println(String.format("Id: %d - Namn: %s",
                               equipment.getEquipmentId(), equipment.getEquipmentName()));
                    }
                    IO.println("");
                    continue;
                }
                try {
                    int equipmentId = Integer.parseInt(requestedEquipmentId);
                    try {
                        Equipment verifiedEquipment = equipmentService.getEquipmentById(equipmentId);
                        if (verifiedEquipment == null) {
                            throw new EquipmentIsNullException();
                        }
                        verifiedEquipmentId = equipmentId;
                        break;
                    } catch (EquipmentIsNullException e) {
                        IO.println(e.getMessage());
                    }
                } catch (NumberFormatException e) {
                    IO.println(stringToIntFailMsgForLoop);
                }

            }

            //TODO Fixa köbokning som inte fungerar
            Loan createdLoan = loanService.startLoan(verifiedEquipmentId, verifiedMember);
            IO.println(String.format("Ditt Lån av %s skapades, artikeln skall återlämnas senast %s",
                    createdLoan.getEquipment().getEquipmentName(), createdLoan.getDateOfReturn().toString()));

        } catch (LoanMaximumLimitException | EquipmentNotAvailableException | UserRevokedException e) {
            IO.println(e.getMessage());
        }
    }
}
