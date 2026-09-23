package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.EquipmentIsNullException;
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

    public Member addToWaitingList(Equipment equipment, Member member) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment, null);
        return waitingListRepository.addMemberToWaitingList(equipment, member);
    }

    public Member getFromWaitingList(Equipment equipment) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment, null);
        return waitingListRepository.getNextMemberInQueue(equipment);
    }

    public Queue<Member> getWaitingListByEquipment(Equipment equipment) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment, null);
        return waitingListRepository.getWaitingListForEquipment(equipment);
    }

    public boolean isMembersInQueueForEquipment(Equipment equipment) throws QueueIsNullException, EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment, null);
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

    private void checkForEquipmentIsNullAndThrow(Equipment equipment, String message) throws EquipmentIsNullException {
        if (message == null) {
            message = "Equipment kan inte vara null";
        }
        if (equipment == null) {
            throw new EquipmentIsNullException(message);
        }
    }
}
