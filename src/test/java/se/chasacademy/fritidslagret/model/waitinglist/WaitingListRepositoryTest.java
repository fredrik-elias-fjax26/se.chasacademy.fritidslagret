package se.chasacademy.fritidslagret.model.waitinglist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.MemberIsNullException;
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

    @Test
    public void shouldThrowNoMemberInputExceptionWhenMissingMemberInAddMemberToWaitingList() {
        // Arrange
        WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
        Member member = null;
        Equipment equipment = new SkiSet("Fake", false,3);

        //Act
        Executable assertThrowAct = () -> waitingListRepository.addMemberToWaitingList(equipment, member);

        //Assert
        assertThrows(MemberIsNullException.class, assertThrowAct);
    }

    @Test
    public void whenNoMembersInQueueAndRunsPollShouldReturnNull () {
        // Arrange
        WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
        Equipment equipment = new SkiSet("Fake", false,3);
        Member member = new Member("Fakemember", MemberStatus.ACTIVE);

        waitingListRepository.addMemberToWaitingList(equipment, member);

        //Act
        Member nullMember = waitingListRepository.getNextMemberInQueue(equipment);

        //Assert
        assertNull(nullMember);

    }
    @Test
    public void whenPollMemberInQueueShouldReturnMemberFirstInQueue () {
        // Arrange
        WaitingListRepository waitingListRepository = WaitingListRepository.getInstance();
        Equipment equipment = new SkiSet("Fake", false,3);
        Member member = new Member("Fakemember", MemberStatus.ACTIVE);
        Member member2 = new Member("Fakemember2", MemberStatus.ACTIVE);

        waitingListRepository.addMemberToWaitingList(equipment, member);
        waitingListRepository.addMemberToWaitingList(equipment, member2);

        //Act
        Member polledMember = waitingListRepository.getNextMemberInQueue(equipment);

        //Assert
        assertEquals(member, polledMember);

    }
}
