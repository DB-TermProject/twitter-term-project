package org.example.domain.post.repository;

import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.post.dto.PostReqDTO.Save;
import static org.example.domain.post.dto.PostResDTO.PostSummary;
import static org.example.domain.post.dto.PostResDTO.PostSummary.toPostSummary;

public class PostRepository {

    public void save(Long id, Save dto) {
        String sql = "INSERT INTO post (content, writer_id) VALUES (?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dto.content());
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public List<PostSummary> findHomeFeed(Long id) {
        String sql = """
            SELECT p.id, p.content, p.like_count, p.comment_count, p.created_at,
                    u.name, u.profile_image_url, u.is_verified
            FROM post p
            JOIN follow f ON f.following_id = p.writer_id
            JOIN user u ON u.id = f.following_id
            WHERE f.follower_id = ? OR u.id = ?
            ORDER BY p.created_at DESC
            LIMIT 50
        """;

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setLong(2, id);

            ResultSet resultSet = statement.executeQuery();

            List<PostSummary> feed = new ArrayList<>();
            while (resultSet.next()) {
                feed.add(toPostSummary(resultSet));
            }

            return feed;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlExecutionException();
        }
    }
}
