package org.example.domain.follow.repository;

import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.follow.dto.FollowReqDTO.Follow;
import static org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import static org.example.domain.follow.dto.FollowResDTO.FollowSummary.toFollowSummary;

public class FollowRepository {

    public void follow(Follow dto, String status) {
        String sql = "INSERT INTO follow (follower_id, following_id, status) values (?, ?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());
            statement.setString(3, status);

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

    public List<FollowSummary> findFollowers(Long id, String status) {
        String sql = "SELECT u.id, u.name, u.organization, u.profile_image_url, u.is_verified " +
                "FROM follow f JOIN user u ON f.follower_id = u.id WHERE following_id = ? AND f.status = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setString(2, status);

            ResultSet resultSet = statement.executeQuery();

            List<FollowSummary> followers = new ArrayList<>();
            while (resultSet.next()) {
                followers.add(toFollowSummary(resultSet));
            }

            return followers;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public List<FollowSummary> findFollowings(Long id, String status) {
        String sql = "SELECT u.id, u.name, u.organization, u.profile_image_url, u.is_verified " +
                "FROM follow f JOIN user u ON f.following_id = u.id WHERE follower_id = ? AND f.status = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.setString(2, status);

            ResultSet resultSet = statement.executeQuery();

            List<FollowSummary> followings = new ArrayList<>();
            while (resultSet.next()) {
                followings.add(FollowSummary.toFollowSummary(resultSet));
            }

            return followings;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void accept(Follow dto) {    // 테스트 대기 중
        String sql = "UPDATE follow SET status = 'ACCEPTED' WHERE following_id = ? AND follower_id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.to());
            statement.setLong(2, dto.from());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public boolean alreadyFollowed(Follow follow) {
        String sql = "SELECT 1 FROM follow WHERE follower_id = ? AND following_id = ? LIMIT 1";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, follow.from());
            statement.setLong(2, follow.to());

            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }
}