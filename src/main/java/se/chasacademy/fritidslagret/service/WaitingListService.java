package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.QueueIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.repository.WaitingListRepository;

import java.util.Queue;

public class WaitingListService {
    private WaitingListRepository waitingListRepository;

    public WaitingListService(WaitingListRepository waitingListRepository) {
        this.waitingListRepository = waitingListRepository;
    }

    public Member addToWaitingList(Equipment equipment, Member member) {
        return member;
    }

    public Member removeFromWaitingList(Equipment equipment, Member member) {
        return member;
    }

    public Queue<Member> getWaitingListByEquipment(Equipment equipment) {
        return waitingListRepository.getWaitingListForEquipment(equipment);
    }

    public boolean isMembersInQueueForEquipment(Equipment equipment) throws QueueIsNullException {
        try {
            return !getWaitingListByEquipment(equipment).isEmpty();
        } catch (NullPointerException e) {
            throw new QueueIsNullException(
                    String.format("Kontakta utvecklaren, Queue kan inte vara null. Felmeddelande: %s",
                            e.getMessage()
                    )
            );
        }
    }
}
