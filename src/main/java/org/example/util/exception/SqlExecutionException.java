package org.example.util.exception;

public class SqlExecutionException extends RuntimeException {
    public SqlExecutionException() {
        super("데이터베이스 작업 중 에러가 발생했습니다.");
    }
}
