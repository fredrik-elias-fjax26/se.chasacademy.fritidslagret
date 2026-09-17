package se.chasacademy.fritidslagret.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.model.loan.Loan;
import se.chasacademy.fritidslagret.service.EquipmentService;
import se.chasacademy.fritidslagret.service.LoanService;
import se.chasacademy.fritidslagret.service.MemberService;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class LoanExpiredTest {
    @Test
    public void loanExpiredTest(){
        //Arrange
        MemberService memberService = mock(MemberService.class);
        EquipmentService equipmentService = mock(EquipmentService.class);
        Clock clock = mock(Clock.class);
        LoanService loanService = new LoanService(memberService, equipmentService, clock);
        Loan loan1 = mock(Loan.class);


        //Act
        when(clock.instant()).thenReturn(Instant.from(Instant.now().plus(1, ChronoUnit.DAYS)));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(loan1.getDateOfReturn()).thenReturn(LocalDate.now());

        //Assert
        assertTrue(loanService.isDelayed(loan1));


    }

}
