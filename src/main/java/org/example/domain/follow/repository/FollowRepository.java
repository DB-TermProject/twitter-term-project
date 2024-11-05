package org.example.domain.follow.repository;

import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.domain.follow.dto.FollowReqDTO.Follow;

public class FollowRepository {

    public void follow(Follow dto) {
        String sql = "INSERT INTO follow (follower_id, following_id) VALUES (?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void unfollow(Follow dto) {
        String sql = "DELETE FROM follow WHERE follower_id = ? AND following_id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }
}