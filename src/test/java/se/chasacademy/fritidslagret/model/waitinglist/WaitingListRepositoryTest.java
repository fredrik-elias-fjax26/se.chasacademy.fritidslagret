package se.chasacademy.fritidslagret.model.waitinglist;

import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.WaitingListRepository;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class WaitingListRepositoryTest {
    @Test
    public void shouldReturnAList() {
        // Arrange
        WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
        Equipment equipment = new SkiSet("Fake", false,3);
        Member member = new Member("Fakemember", MemberStatus.ACTIVE);
        waitingListRepository.addMemberToWaitingList(equipment, member);

        // Act
        Queue<?> queuedWaitingList = waitingListRepository.getWaitingListForEquipment(equipment);

        // Assert
        assertNotNull(queuedWaitingList);
    }
    @Test
    public void shouldAddMemberToWaitingList() {
        // Arrange
        WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
        Member member = new Member("Member", MemberStatus.ACTIVE);
        Equipment equipment = new SkiSet("Fake", false,3);

        // Act
        waitingListRepository.addMemberToWaitingList(equipment, member);

        // Assert
        assertEquals(member, waitingListRepository.getNextMemberInQueue(equipment));

    }
}
