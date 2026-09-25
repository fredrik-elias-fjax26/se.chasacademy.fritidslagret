package se.chasacademy.fritidslagret.exception;

public class UserInactiveException extends RuntimeException {
        public UserInactiveException() {
            String message = "Medlemmen har ett inaktivt medlemskap!";
            super(message);
        }
    }


