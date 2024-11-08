package org.example.domain.comment.repository;

import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.domain.comment.dto.CommentReqDTO.Save;

public class CommentRepository {

    public void save(Long id, String content, Long postId) {  // Parent Comment
        String sql = "INSERT INTO comment (content, writer_id, post_id) VALUES (?, ?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, content);
            statement.setLong(2, id);
            statement.setLong(3, postId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void save(Long id, Save dto) {    // Child Comment
        String sql = "INSERT INTO comment (content, writer_id, post_id, parent_comment_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dto.content());
            statement.setLong(2, id);
            statement.setLong(3, dto.postId());
            statement.setLong(4, dto.parentCommentId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }
}
