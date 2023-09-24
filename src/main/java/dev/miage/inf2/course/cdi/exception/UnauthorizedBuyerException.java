package dev.miage.inf2.course.cdi.exception;

public class UnauthorizedBuyerException extends RuntimeException {
    public UnauthorizedBuyerException(String msg) {
        super(msg);
    }
}
