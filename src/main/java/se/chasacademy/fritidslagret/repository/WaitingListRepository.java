package se.chasacademy.fritidslagret.repository;

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
        return membersWaitingForEquipment.get(equipment);
    }

    public Member getNextMemberInQueue(Equipment equipment) {
        return membersWaitingForEquipment.get(equipment).poll();
    }

    public Member addMemberToWaitingList(Equipment equipment, Member member) {
        if (!membersWaitingForEquipment.containsKey(equipment)) {
            membersWaitingForEquipment.put(equipment, new LinkedList<>());
        }
        membersWaitingForEquipment.get(equipment).add(member);
        return member;
    }

    public Member removeMemberFromWaitingList(Equipment equipment, Member member) {
        membersWaitingForEquipment.get(equipment).remove(member);
        return member;
    }
}
