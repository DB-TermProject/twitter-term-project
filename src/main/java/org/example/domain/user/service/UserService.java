package org.example.domain.user.service;

import org.example.domain.user.dto.UserReqDTO.Login;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO;
import org.example.domain.user.dto.UserResDTO.ProfileUpdateResponse;
import org.example.domain.user.repository.UserRepository;
import org.example.util.exception.PasswordMismatchException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.domain.user.dto.UserReqDTO.Password;
import static org.example.domain.user.dto.UserReqDTO.Profile;

public class UserService {

    public final UserRepository userRepository = new UserRepository();

    public Long findId(Login dto, Connection connection) throws SQLException {
        return userRepository.findIdByEmailAndPassword(dto.email(), dto.password(), connection);
    }

    public void save(SignUp dto, Connection connection) throws SQLException {
        userRepository.save(dto, connection);
    }

    public Boolean existsByEmail(String email, Connection connection) throws SQLException {
        return userRepository.existsByEmail(email, connection);
    }

    public ProfileUpdateResponse updateProfile(Long userId, Profile dto, Connection connection) throws SQLException {
        userRepository.updateProfile(userId, dto, connection);
        return new ProfileUpdateResponse(dto.info(), dto.profile_image_url(), dto.organization(), dto.is_public());
    }

    public void updatePassword(Long userId, Password dto, Connection connection) throws SQLException {
        userRepository.updatePassword(userId, dto.newPassword(), connection);
    }

    public boolean isPublic(Long userId, Connection connection) throws SQLException {
        return userRepository.isPublic(userId, connection);
    }

    public void updateFollowCount(Long from, Long to, Long value, Connection connection) throws SQLException {
        userRepository.updateFollowingCount(from, value, connection);
        userRepository.updateFollowerCount(to, value, connection);
    }

    public String findName(Long userId, Connection connection) throws SQLException {
        return userRepository.findName(userId, connection);
    }

    public UserResDTO.Profile findProfile(Long userId, Connection connection) throws SQLException {
        return userRepository.findById(userId, connection);
    }
}
