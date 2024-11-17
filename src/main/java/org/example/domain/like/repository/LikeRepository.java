package org.example.domain.like.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface LikeRepository {

    void save(Long userId, Long id, Connection connection) throws SQLException;

    void delete(Long userId, Long id, Connection connection) throws SQLException;
}
