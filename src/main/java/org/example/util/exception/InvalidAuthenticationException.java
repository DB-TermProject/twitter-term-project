package org.example.util.exception;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("아이디 및 비밀번호를 확인해주세요.");
    }
}
