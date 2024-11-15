package org.example.domain.user.service;

import org.example.domain.user.dto.UserReqDTO.Login;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO.LoginResponse;
import org.example.domain.user.dto.UserResDTO.ProfileResponse;
import org.example.domain.user.repository.UserRepository;
import org.example.util.exception.InvalidAuthenticationException;
import org.example.util.exception.PasswordMismatchException;
import org.example.util.exception.UserExistsException;

import static org.example.domain.user.dto.UserReqDTO.Password;
import static org.example.domain.user.dto.UserReqDTO.Profile;

public class UserService {

    public final UserRepository userRepository = new UserRepository();

    public LoginResponse login(Login dto) {
        Long id = userRepository.findIdByEmailAndPassword(dto.email(), dto.password())    // Hashing?
                .orElseThrow(InvalidAuthenticationException::new);

        return new LoginResponse(id);
    }

    public void signUp(SignUp dto) {
        dto.validate();

        if (userRepository.existsByEmail(dto.email()))
            throw new UserExistsException();

        userRepository.save(dto);   // 회원가입 후 로그인 페이지로 리다이렉트
    }

    public ProfileResponse updateProfile(Long userId, Profile dto) {
        dto.validate();
        userRepository.updateProfile(userId, dto);
        return new ProfileResponse(dto.info(), dto.profile_image_url(), dto.organization(), dto.is_public());
    }

    public void updatePassword(Long userId, Password dto) {
        dto.validate();

        if(dto.passwordMatches())    // DTO method
            throw new PasswordMismatchException();

        userRepository.updatePassword(userId, dto.newPassword());
    }

    public boolean isPublic(Long userId) {
        return userRepository.isPublic(userId);
    }

    public void updateFollowCount(Long from, Long to, Long value) {
        userRepository.updateFollowingCount(from, value);
        userRepository.updateFollowerCount(to, value);
    }

    public String findName(Long userId) {
        return userRepository.findName(userId);
    }
}
