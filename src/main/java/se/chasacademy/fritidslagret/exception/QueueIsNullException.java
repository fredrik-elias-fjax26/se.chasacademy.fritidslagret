package se.chasacademy.fritidslagret.exception;

public class QueueIsNullException extends RuntimeException {
    public QueueIsNullException(String message) {
        super(message);
    }
}
