package org.example.domain.follow.repository;

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

    public void save(Follow dto, String status, Connection connection) throws SQLException {
        String sql = "INSERT INTO follow (follower_id, following_id, status) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());
            statement.setString(3, status);
            statement.executeUpdate();
        }
    }

    public void delete(Follow dto, Connection connection) throws SQLException {
        String sql = "DELETE FROM follow WHERE follower_id = ? AND following_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());
            statement.executeUpdate();
        }
    }

    public List<FollowSummary> findFollowers(Long id, String status, Connection connection) throws SQLException {
        String sql = "SELECT u.id, u.name, u.organization, u.profile_image_url, u.is_verified " +
                "FROM follow f JOIN user u ON f.follower_id = u.id WHERE following_id = ? AND f.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setString(2, status);

            ResultSet resultSet = statement.executeQuery();
            List<FollowSummary> followers = new ArrayList<>();
            while (resultSet.next()) {
                followers.add(toFollowSummary(resultSet));
            }

            return followers;
        }
    }

    public List<FollowSummary> findFollowings(Long id, String status, Connection connection) throws SQLException {
        String sql = "SELECT u.id, u.name, u.organization, u.profile_image_url, u.is_verified " +
                "FROM follow f JOIN user u ON f.following_id = u.id WHERE follower_id = ? AND f.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setString(2, status);

            ResultSet resultSet = statement.executeQuery();
            List<FollowSummary> followings = new ArrayList<>();
            while (resultSet.next()) {
                followings.add(FollowSummary.toFollowSummary(resultSet));
            }

            return followings;
        }
    }

    public void updateStatus(Follow dto, Connection connection) throws SQLException {    // 테스트 대기 중
        String sql = "UPDATE follow SET status = 'ACCEPTED' WHERE following_id = ? AND follower_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dto.to());
            statement.setLong(2, dto.from());
            statement.executeUpdate();
        }
    }

    public boolean alreadyFollowed(Follow follow, Connection connection) throws SQLException {
        String sql = "SELECT 1 FROM follow WHERE follower_id = ? AND following_id = ? LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, follow.from());
            statement.setLong(2, follow.to());
            return statement.executeQuery().next();
        }
    }
}