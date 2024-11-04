package org.example.util.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("이미 가입된 유저입니다.");
    }
}
