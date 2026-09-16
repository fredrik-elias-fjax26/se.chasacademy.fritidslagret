package se.chasacademy.fritidslagret.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.service.LoanService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class LoanExpiredTest {
    @Test
    public void loanExpiredTest(){
        //Arrange
        Clock clock = mock(Clock.class);
        Loan loan1 = mock(Loan.class);


        //Act

        when(loan1.getDateOfReturn()).thenReturn(LocalDate.now());
        when(clock).thenReturn(clock.);
        when(LocalDate.now()).thenReturn(LocalDate.now(clock));


        //Assert


    }

}
