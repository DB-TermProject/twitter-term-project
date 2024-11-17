package org.example.domain.like.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface LikeService {

    void save(Long userId, Long id, Connection connection) throws SQLException;

    void delete(Long userId, Long id, Connection connection) throws SQLException;
}
