package se.chasacademy.fritidslagret.ui;

import org.junit.jupiter.api.Test;
import se.chasacademy.fritidslagret.Main;
import se.chasacademy.fritidslagret.exception.ConfirmationFailedException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentMenuDeleteTest {
    @Test
    public void UIshouldThrowConfirmationFailedExceptionWhenWrongConfirmationNameIsInput() {
        /*
        Test implemented with help of AI

        * Prompt: How can I walk through a menu and catch the output of an exception?

        AI build structure of test and I used Stream parts from suggestion, but removed AI hardcoded response message
        from Exception. If you change text in Exception test will fail. Created an instance of an exception and read
        the message from that "the usual way". Therefore, the message is always up-to-date

        This test also demanded movement of main compact class to inside of project which was also on my mind anyway.
        So that did get refactored also.

        Gradle.build also needed improvements because of parallell run of tests the Input/Output streams get mixed.
        This test needs to run separate, all ui walk through will need.

        */

        // Save Default Input/Output Stream !!!! IMPORTANT !!!! (Must have Default Input stream saved for restore)
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;

        try {
            // Arrange

            String uiWalkThrough =
                    "1\n" +         // EquipmentMenu
                    "4\n" +         // DeleteMenu
                    "1\n" +         // Choose Equipment
                    "abcdefgh\n" +    // Now standing at confirmationDialog
                    "5\n" +         // Going out of menu
                    "7\n"           // Exit Program
                    ;

            ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
            ByteArrayInputStream fakedInput = new ByteArrayInputStream(uiWalkThrough.getBytes());

            ConfirmationFailedException confirmationFailedException = new ConfirmationFailedException();

            System.setIn(fakedInput);
            System.setOut(new PrintStream(consoleOutput));

            // Act
            Main.main();


            // Assert
            String consoleOutputString = consoleOutput.toString();
            assertTrue(consoleOutputString.contains(confirmationFailedException.getMessage()));

        } finally {
            // Restore Default InputStream !!!! IMPORTANT  !!!!
            System.setIn(stdIn);
            System.setOut(stdOut);
        }
    }


}
