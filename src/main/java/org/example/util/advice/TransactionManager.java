package org.example.util.advice;

import org.example.util.config.JdbcConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public interface TransactionCallback<T> {
        T doInTransaction(Connection connection) throws Exception;
    }

    public interface VoidTransactionCallback {
        void doInTransaction(Connection connection) throws Exception;
    }

    public <T> T execute(TransactionCallback<T> callback) {
        try (Connection connection = JdbcConfig.getConnection()) {
            try {
                T result = callback.doInTransaction(connection);
                connection.commit(); // 성공 시 커밋
                return result;
            } catch (Exception e) {
                rollback(connection);
                throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }

    public void execute(VoidTransactionCallback callback) {
        try (Connection connection = JdbcConfig.getConnection()) {
            try {
                callback.doInTransaction(connection);
                connection.commit(); // 성공 시 커밋
            } catch (Exception e) {
                rollback(connection);
                throw new RuntimeException("Transaction failed", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error", e);
        }
    }

    public <T> T executeReadOnly(TransactionCallback<T> callback) {
        try (Connection connection = JdbcConfig.getConnection()) {
            connection.setAutoCommit(true); // 읽기 전용 모드
            return callback.doInTransaction(connection);
        } catch (Exception e) {
            throw new RuntimeException("Read-only transaction failed: " + e.getMessage(), e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Rollback failed: " + e.getMessage(), e);
        }
    }
}
