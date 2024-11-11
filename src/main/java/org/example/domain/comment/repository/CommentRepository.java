package org.example.domain.comment.repository;

import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.comment.dto.CommentReqDTO.Save;
import static org.example.domain.comment.dto.CommentResDTO.Detail;
import static org.example.domain.comment.dto.CommentResDTO.Detail.toDetail;

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

    public List<Detail> findComments(Long postId) {
        String sql = """
            SELECT c.id, c.content, c.like_count,
                   CASE 
                       WHEN c.updated_at IS NOT NULL THEN c.updated_at 
                       ELSE c.created_at 
                   END AS created_at,
                   u.name, u.profile_image_url, u.is_verified,
                   c.parent_comment_id
            FROM comment c
            JOIN post p ON p.id = c.post_id
            JOIN user u ON u.id = c.writer_id
            WHERE p.id = ? AND c.parent_comment_id IS NULL
            ORDER BY created_at DESC
        """;

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();

            List<Detail> comments = new ArrayList<>();
            while (resultSet.next()) {
                Detail comment = toDetail(resultSet);
                List<Detail> childComments = findChildComments(comment.id());
                comment = comment.withChildComments(childComments);
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    private List<Detail> findChildComments(Long parentId) {
        String sql = """
            SELECT c.id, c.content, c.like_count,
                   CASE 
                       WHEN c.updated_at IS NOT NULL THEN c.updated_at 
                       ELSE c.created_at 
                   END AS created_at,
                   u.name, u.profile_image_url, u.is_verified
            FROM comment c
            JOIN user u ON u.id = c.writer_id
            WHERE c.parent_comment_id = ?
            ORDER BY created_at DESC
        """;

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, parentId);
            ResultSet resultSet = statement.executeQuery();

            List<Detail> childComments = new ArrayList<>();
            while (resultSet.next()) {
                Detail childComment = toDetail(resultSet);
                List<Detail> grandChildComments = findChildComments(childComment.id());
                childComment = childComment.withChildComments(grandChildComments);
                childComments.add(childComment);
            }

            return childComments;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public Long delete(Long id) {
        String countSql = "SELECT COUNT(*) FROM comment WHERE parent_comment_id = ? OR id = ?";
        String deleteSql = "DELETE FROM comment WHERE id = ?";

        long deletedCount = 0L;

        try (Connection connection = JdbcConfig.getConnection()) {
            try (PreparedStatement countStatement = connection.prepareStatement(countSql)) {
                countStatement.setLong(1, id);
                countStatement.setLong(2, id);

                try (ResultSet rs = countStatement.executeQuery()) {
                    if (rs.next()) {
                        deletedCount = rs.getLong(1);
                    }
                }
            }

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setLong(1, id);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }

        return deletedCount;
    }

}
