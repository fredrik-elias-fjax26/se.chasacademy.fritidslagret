package se.chasacademy.fritidslagret.service;

import se.chasacademy.fritidslagret.exception.EquipmentIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.repository.WaitingListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class WaitingListService {
    private final WaitingListRepository waitingListRepository;

    public WaitingListService(WaitingListRepository waitingListRepository) {
        this.waitingListRepository = waitingListRepository;
    }

    public Member addToWaitingList(Equipment equipment, Member member) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment);
        return waitingListRepository.addMemberToWaitingList(equipment, member);
    }

    public Member getFromWaitingList(Equipment equipment) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment);
        return waitingListRepository.getNextMemberInQueue(equipment);
    }

    public Queue<Member> getWaitingListByEquipment(Equipment equipment) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment);
        return waitingListRepository.getWaitingListForEquipment(equipment);
    }

    public boolean isMembersInQueueForEquipment(Equipment equipment) throws EquipmentIsNullException {
        checkForEquipmentIsNullAndThrow(equipment);
        return !getWaitingListByEquipment(equipment).isEmpty();
    }

    private void checkForEquipmentIsNullAndThrow(Equipment equipment) throws EquipmentIsNullException {
        if (equipment == null) {
            throw new EquipmentIsNullException();
        }
    }

    public List<Equipment> getAllEquipmentInWaitingList() {
        Map<Equipment, Queue<Member>> gettedMap = waitingListRepository.getWaitingListMap();
        List<Equipment> equipmentListWithActiveQueue = new ArrayList<>();
        for (Equipment equipment : gettedMap.keySet()) {
            if (gettedMap.get(equipment).isEmpty()) {
                continue;
            }
            equipmentListWithActiveQueue.add(equipment);
        }
        return equipmentListWithActiveQueue;
    }
}
