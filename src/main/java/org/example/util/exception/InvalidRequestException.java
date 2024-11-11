package org.example.util.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("올바르지 않은 요청입니다.");
    }
}