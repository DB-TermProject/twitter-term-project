package org.example.domain.like.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentLikeRepository implements LikeRepository {

    @Override
    public void save(Long userId, Long commentId, Connection connection) throws SQLException {
        String sql = "INSERT INTO comment_like (liker_id, comment_id) values (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, commentId);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long userId, Long commentId, Connection connection) throws SQLException {
        String sql = "DELETE FROM comment_like WHERE liker_id = ? AND comment_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            statement.setLong(2, commentId);
            statement.executeUpdate();
        }
    }
}
