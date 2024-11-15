package org.example.domain.user.repository;

import org.example.domain.comment.dto.CommentResDTO;
import org.example.domain.user.dto.UserReqDTO.Profile;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO;
import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.domain.comment.dto.CommentResDTO.Detail.toDetail;

public class UserRepository {

    public Optional<Long> findIdByEmailAndPassword(String email, String password) {
        String sql = "SELECT id FROM user WHERE email = ? AND password = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
                return Optional.of(resultSet.getLong("id"));
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ? LIMIT 1";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public boolean isPublic(Long id) {
        String sql = "SELECT is_public FROM user WHERE id = ?";
        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            boolean isPublic = false;
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                isPublic = resultSet.getBoolean("is_public");

            return isPublic;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlExecutionException();
        }
    }

    public void save(SignUp dto) {
        String sql = "INSERT INTO user (email, password, name) VALUES (?, ?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dto.email());
            statement.setString(2, dto.password());
            statement.setString(3, dto.name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void updateProfile(Long id, Profile dto) {
        String sql = "UPDATE user SET info = ?, profile_image_url = ?, organization = ?, is_public = ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, dto.info());
            statement.setString(2, dto.profile_image_url());
            statement.setString(3, dto.organization());
            statement.setBoolean(4, dto.is_public());
            statement.setLong(5, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void updatePassword(Long id, String password) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, password);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void updateFollowingCount(Long id, Long value) {
        String sql = "UPDATE user SET following_count = following_count + ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, value);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public void updateFollowerCount(Long id, long value) {
        String sql = "UPDATE user SET followers_count = followers_count + ? WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, value);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public String findName(Long id) {
        String sql = "SELECT name FROM user WHERE id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return resultSet.getString("name");
            throw new SqlExecutionException();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public UserResDTO.Profile findById(Long id) {
        String sql = """
            SELECT u.id, u.email, u.name, u.info, u.profile_image_url, u.organization, u.is_verified, u.is_public, u.followers_count, u.following_count
            FROM user u
            WHERE u.id = ?
        """;

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return UserResDTO.Profile.toProfile(resultSet);
            throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlExecutionException();
        }
    }
}