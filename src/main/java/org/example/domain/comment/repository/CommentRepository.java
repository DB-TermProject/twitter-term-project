package org.example.domain.comment.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.comment.dto.CommentReqDTO.Save;
import static org.example.domain.comment.dto.CommentReqDTO.Update;
import static org.example.domain.comment.dto.CommentResDTO.Detail;
import static org.example.domain.comment.dto.CommentResDTO.Detail.toDetail;

public class CommentRepository {

    public void save(Long id, String content, Long postId, Connection connection) throws SQLException {  // Parent Comment
        String sql = "INSERT INTO comment (content, writer_id, post_id) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, content);
            statement.setLong(2, id);
            statement.setLong(3, postId);
            statement.executeUpdate();
        }
    }

    public void save(Long id, Save dto, Connection connection) throws SQLException {    // Child Comment
        String sql = "INSERT INTO comment (content, writer_id, post_id, parent_comment_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dto.content());
            statement.setLong(2, id);
            statement.setLong(3, dto.postId());
            statement.setLong(4, dto.parentCommentId());
            statement.executeUpdate();
        }
    }

    public List<Detail> findComments(Long postId, Connection connection) throws SQLException {
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, postId);
            ResultSet resultSet = statement.executeQuery();

            List<Detail> comments = new ArrayList<>();
            while (resultSet.next()) {
                Detail comment = toDetail(resultSet);
                List<Detail> childComments = findChildComments(comment.id(), connection);
                comment = comment.withChildComments(childComments);
                comments.add(comment);
            }
            return comments;
        }
    }

    private List<Detail> findChildComments(Long parentId, Connection connection) throws SQLException {
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

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, parentId);
            ResultSet resultSet = statement.executeQuery();

            List<Detail> childComments = new ArrayList<>();
            while (resultSet.next()) {
                Detail childComment = toDetail(resultSet);
                List<Detail> grandChildComments = findChildComments(childComment.id(), connection);
                childComment = childComment.withChildComments(grandChildComments);
                childComments.add(childComment);
            }

            return childComments;
        }
    }

    public Long delete(Long id, Connection connection) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM comment WHERE parent_comment_id = ? OR id = ?";
        String deleteSql = "DELETE FROM comment WHERE id = ?";

        long deletedCount = 0L;

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

        return deletedCount;
    }

    public void updateComment(Long id, Update dto, Connection connection) throws SQLException {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dto.content());
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public void updateLikeCount(Long id, Long value, Connection connection) throws SQLException {
        String lockSql = "SELECT like_count FROM comment WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE comment SET like_count = like_count + ? WHERE id = ?";

        try (PreparedStatement lockStatement = connection.prepareStatement(lockSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

            lockStatement.setLong(1, id);
            lockStatement.executeQuery();

            updateStatement.setLong(1, value);
            updateStatement.setLong(2, id);
            updateStatement.executeUpdate();
        }
    }


    public Long findWriter(Long commentId, Connection connection) throws SQLException {
        String sql = "SELECT writer_id FROM comment WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, commentId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getLong("writer_id");

            throw new SQLException();
        }
    }
}
