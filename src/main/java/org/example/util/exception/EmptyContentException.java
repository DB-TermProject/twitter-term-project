package org.example.util.exception;

public class EmptyContentException extends RuntimeException {
    public EmptyContentException() {
        super("내용을 입력해주세요.");
    }
}
