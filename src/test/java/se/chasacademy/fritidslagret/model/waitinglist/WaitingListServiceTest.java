package se.chasacademy.fritidslagret.model.waitinglist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import se.chasacademy.fritidslagret.exception.QueueIsNullException;
import se.chasacademy.fritidslagret.model.equipment.Equipment;
import se.chasacademy.fritidslagret.model.equipment.SkiSet;
import se.chasacademy.fritidslagret.model.member.Member;
import se.chasacademy.fritidslagret.model.member.MemberStatus;
import se.chasacademy.fritidslagret.repository.WaitingListRepository;
import se.chasacademy.fritidslagret.service.WaitingListService;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WaitingListServiceTest {
    @Test
    public void shouldGetWaitingListByEquipmentFromWaitingListRepository() {
        //Arrange
        WaitingListRepository waitingListRepository = mock(WaitingListRepository.class);
        WaitingListService waitingListService = new WaitingListService(waitingListRepository);

        Equipment equipment = new SkiSet("Fake", true, 30);
        Queue<Member> memberQueue = new LinkedList<Member>();

        //Act
        when(waitingListRepository.getWaitingListForEquipment(equipment)).thenReturn(memberQueue);
        waitingListService.getWaitingListByEquipment(equipment);

        //Assert
        verify(waitingListRepository).getWaitingListForEquipment(equipment);
    }

    @Test
    public void returnTrueIfEquipmentHasMemberInQueue() {
        // Arrange
        WaitingListRepository waitingListRepository = mock(WaitingListRepository.class);
        WaitingListService waitingListService = new WaitingListService(waitingListRepository);
        Equipment equipment = new SkiSet("Fake", true, 30);
        Member member = new Member("Fake", MemberStatus.ACTIVE);
        Queue<Member> memberQueue = new LinkedList<>();
        memberQueue.add(member);

        // Act
        when(waitingListRepository.getWaitingListForEquipment(equipment)).thenReturn(memberQueue);


        // Assert
        assertTrue(waitingListService.isMembersInQueueForEquipment(equipment));
        verify(waitingListRepository).getWaitingListForEquipment(equipment);
    }

    @Test
    public void returnFalseIfQueueIsEmptyAfterAddingQueueAndPollingFromQueue() {
        // Arrange
        WaitingListRepository waitingListRepository = mock(WaitingListRepository.class);
        WaitingListService waitingListService = new WaitingListService(waitingListRepository);
        Equipment equipment = new SkiSet("Fake", true, 30);
        Member member = new Member("Fake", MemberStatus.ACTIVE);
        Queue<Member> memberQueue = new LinkedList<>();
        memberQueue.add(member);
        memberQueue.poll();

        // Act
        when(waitingListRepository.getWaitingListForEquipment(equipment)).thenReturn(memberQueue);


        // Assert
        assertFalse(waitingListService.isMembersInQueueForEquipment(equipment));
        verify(waitingListRepository).getWaitingListForEquipment(equipment);
    }

    @Test
    public void throwExceptionIfQueueIsNull() {
        // Arrange
        WaitingListRepository waitingListRepository = mock(WaitingListRepository.class);
        WaitingListService waitingListService = new WaitingListService(waitingListRepository);
        Equipment equipment = new SkiSet("Fake", true, 30);
        Queue<Member> memberQueue = null;

        // Act
        when(waitingListRepository.getWaitingListForEquipment(equipment)).thenReturn(memberQueue);
        Executable actForAssertThrows = () -> waitingListService.isMembersInQueueForEquipment(equipment);

        // Assert
        assertThrows(QueueIsNullException.class, actForAssertThrows);
        verify(waitingListRepository).getWaitingListForEquipment(equipment);
    }

}
