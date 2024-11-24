package org.example.domain.post.repository;

import org.example.domain.post.dto.PostResDTO.Detail;
import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.post.dto.PostReqDTO.Save;
import static org.example.domain.post.dto.PostResDTO.Detail.toDetail;

public class PostRepository {

    public void save(Long id, Save dto, Connection connection) {
        String sql = "INSERT INTO post (content, writer_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dto.content());
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public List<Detail> findHomeFeed(Long id, Connection connection) {
        String sql = """
           SELECT p.id, p.content, p.like_count, p.comment_count, p.is_pinned,
                                   CASE
                                       WHEN p.updated_at IS NOT NULL THEN p.updated_at
                                       ELSE p.created_at
                                       END AS created_at,
                                   u.name, u.profile_image_url, u.is_verified
                            FROM post p
                                     JOIN user u ON u.id = p.writer_id
                            WHERE u.id IN (SELECT following_id FROM follow WHERE follower_id = ?)
                                    OR u.id = ?
                            ORDER BY created_at DESC
                            LIMIT 50
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setLong(2, id);

            ResultSet resultSet = statement.executeQuery();

            List<Detail> feed = new ArrayList<>();
            while (resultSet.next()) {
                feed.add(toDetail(resultSet));
            }

            return feed;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public List<Detail> findUserFeed(Long id) {
        String sql = """
            SELECT p.id, p.content, p.like_count, p.comment_count, p.is_pinned,
                   CASE 
                       WHEN p.updated_at IS NOT NULL THEN p.updated_at 
                       ELSE p.created_at 
                   END AS created_at,
                   u.name, u.profile_image_url, u.is_verified
            FROM post p        
            JOIN user u ON u.id = p.writer_id
            WHERE u.id = ?
            ORDER BY created_at DESC
            LIMIT 50
        """;

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            List<Detail> feed = new ArrayList<>();
            while (resultSet.next()) {
                feed.add(toDetail(resultSet));
            }

            return feed;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public Detail findPost(Long id, Connection connection) {
        String sql = """
            SELECT p.id, p.content, p.like_count, p.comment_count, p.is_pinned,
                   CASE 
                       WHEN p.updated_at IS NOT NULL THEN p.updated_at 
                       ELSE p.created_at 
                   END AS created_at,
                   u.name, u.profile_image_url, u.is_verified
            FROM post p        
            JOIN user u ON u.id = p.writer_id
            WHERE p.id = ?
            ORDER BY created_at DESC
            LIMIT 50
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return toDetail(resultSet);
            throw new SqlExecutionException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlExecutionException();
        }
    }

    public void updateCommentCount(Long id, Long value) {
        String lockSql = "SELECT comment_count FROM post WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE post SET comment_count = comment_count + ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement lockStatement = connection.prepareStatement(lockSql);
                 PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

                lockStatement.setLong(1, id);
                lockStatement.executeQuery();

                updateStatement.setLong(1, value);
                updateStatement.setLong(2, id);
                updateStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }


    public void updateLikeCount(Long id, Long value) {
        String lockSql = "SELECT like_count FROM post WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE post SET like_count = like_count + ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement lockStatement = connection.prepareStatement(lockSql);
                 PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

                lockStatement.setLong(1, id);
                lockStatement.executeQuery();

                updateStatement.setLong(1, value);
                updateStatement.setLong(2, id);
                updateStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }


    public Long findWriter(Long postId) {
        String sql = "SELECT writer_id FROM post WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, postId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getLong("writer_id");
            throw new SqlExecutionException();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }
}
