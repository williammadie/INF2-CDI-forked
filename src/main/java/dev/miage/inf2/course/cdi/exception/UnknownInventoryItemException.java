package dev.miage.inf2.course.cdi.exception;

public class UnknownInventoryItemException extends RuntimeException {
    public UnknownInventoryItemException(String msg) {
        super(msg);
    }
}
