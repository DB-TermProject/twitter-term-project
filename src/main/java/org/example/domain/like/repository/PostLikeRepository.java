package org.example.domain.like.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostLikeRepository implements LikeRepository {

    @Override
    public void save(Long userId, Long postId, Connection connection) throws SQLException {
        String sql = "INSERT INTO post_like (liker_id, post_id) values (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, postId);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long userId, Long postId, Connection connection) throws SQLException {
        String sql = "DELETE FROM post_like WHERE liker_id = ? AND post_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, postId);
            statement.executeUpdate();
        }
    }
}
