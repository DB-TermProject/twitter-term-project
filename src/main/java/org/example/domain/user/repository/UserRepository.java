package org.example.domain.user.repository;

import org.example.domain.user.dto.UserReqDTO.Profile;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public Long findIdByEmailAndPassword(String email, String password, Connection connection) throws SQLException {
        String sql = "SELECT id FROM user WHERE email = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
                return resultSet.getLong("id");

            throw new SQLException();
        }
    }

    public boolean existsByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT 1 FROM user WHERE email = ? LIMIT 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            return statement.executeQuery().next();
        }
    }

    public boolean isPublic(Long id, Connection connection) throws SQLException {
        String sql = "SELECT is_public FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getBoolean("is_public");

            throw new SQLException();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void save(SignUp dto, Connection connection) throws SQLException {
        String sql = "INSERT INTO user (email, password, name) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dto.email());
            statement.setString(2, dto.password());
            statement.setString(3, dto.name());
            statement.executeUpdate();
        }
    }

    public void updateProfile(Long id, Profile dto, Connection connection) throws SQLException {
        String sql = "UPDATE user SET info = ?, profile_image_url = ?, organization = ?, is_public = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dto.info());
            statement.setString(2, dto.profile_image_url());
            statement.setString(3, dto.organization());
            statement.setBoolean(4, dto.is_public());
            statement.setLong(5, id);
            statement.executeUpdate();
        }
    }

    public void updatePassword(Long id, String password, Connection connection) throws SQLException {
        String sql = "UPDATE user SET password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, password);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public void updateFollowingCount(Long id, Long value, Connection connection) throws SQLException {
        String lockSql = "SELECT following_count FROM user WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE user SET following_count = following_count + ? WHERE id = ?";

        try (PreparedStatement lockStatement = connection.prepareStatement(lockSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            lockStatement.setLong(1, id);
            lockStatement.executeQuery();

            updateStatement.setLong(1, value);
            updateStatement.setLong(2, id);
            updateStatement.executeUpdate();
        }
    }


    public void updateFollowerCount(Long id, long value, Connection connection) throws SQLException {
        String lockSql = "SELECT followers_count FROM user WHERE id = ? FOR UPDATE";
        String updateSql = "UPDATE user SET followers_count = followers_count + ? WHERE id = ?";

        try (PreparedStatement lockStatement = connection.prepareStatement(lockSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            lockStatement.setLong(1, id);
            lockStatement.executeQuery();

            updateStatement.setLong(1, value);
            updateStatement.setLong(2, id);
            updateStatement.executeUpdate();
        }
    }


    public String findName(Long id, Connection connection) throws SQLException {
        String sql = "SELECT name FROM user WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return resultSet.getString("name");

            throw new SqlExecutionException();
        }
    }

    public UserResDTO.Profile findById(Long id, Connection connection) throws SQLException {
        String sql = """
            SELECT u.id, u.email, u.name, u.info, u.profile_image_url, u.organization, u.is_verified, u.is_public, u.followers_count, u.following_count
            FROM user u
            WHERE u.id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return UserResDTO.Profile.toProfile(resultSet);
            throw new SQLException();
        }
    }
}