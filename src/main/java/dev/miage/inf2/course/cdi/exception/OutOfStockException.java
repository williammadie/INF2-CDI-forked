package dev.miage.inf2.course.cdi.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String msg) {
        super(msg);
    }
}
