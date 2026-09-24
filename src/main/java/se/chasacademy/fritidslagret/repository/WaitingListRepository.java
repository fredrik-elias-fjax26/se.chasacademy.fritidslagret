package se.chasacademy.fritidslagret.repository;

import se.chasacademy.fritidslagret.exception.EquipmentIsNullException;
import se.chasacademy.fritidslagret.exception.MemberIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.member.Member;

import java.util.*;

public class WaitingListRepository {
    private final Map<Equipment, Queue<Member>> membersWaitingForEquipment = new HashMap<>();
    private static WaitingListRepository instance = null;

    private WaitingListRepository() {
    }

    public static WaitingListRepository getInstance() {
        if (instance == null) {
            instance = new WaitingListRepository();
        }
        return instance;
    }

    public Queue<Member> getWaitingListForEquipment(Equipment equipment) {
        if (equipment == null) {
            throw new EquipmentIsNullException();
        }
        return membersWaitingForEquipment.getOrDefault(equipment, new LinkedList<>());
    }

    public Member getNextMemberInQueue(Equipment equipment) {
        if (equipment == null) {
            throw new EquipmentIsNullException();
        }
        return getWaitingListForEquipment(equipment).poll();
    }

    public Member addMemberToWaitingList(Equipment equipment, Member member) {
        if (equipment == null) {
            throw new EquipmentIsNullException();
        }

        if (member == null) {
            throw new MemberIsNullException();
        }

        if (!membersWaitingForEquipment.containsKey(equipment)) {
            membersWaitingForEquipment.put(equipment, new LinkedList<>());
        }

        membersWaitingForEquipment.get(equipment).add(member);
        return member;
    }

    public Map<Equipment, Queue<Member>> getWaitingListMap() {
            return membersWaitingForEquipment;
    }
}
