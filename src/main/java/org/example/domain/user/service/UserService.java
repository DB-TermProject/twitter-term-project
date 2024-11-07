package org.example.domain.user.service;

import org.example.domain.user.dto.UserReqDTO.Login;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO.LoginResponse;
import org.example.domain.user.dto.UserResDTO.ProfileResponse;
import org.example.domain.user.repository.UserRepository;
import org.example.util.exception.InvalidAuthenticationException;
import org.example.util.exception.PasswordMismatchException;
import org.example.util.exception.UserExistsException;

import javax.swing.*;
import java.sql.Connection;

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

    public ProfileResponse updateProfile(Long id, Profile dto) {
        dto.validate();
        userRepository.updateProfile(id, dto);
        return new ProfileResponse(dto.info(), dto.profile_image_url(), dto.organization(), dto.is_public());
    }

    public void updatePassword(Long id, Password dto) {
        dto.validate();

        if(!dto.newPassword().equals(dto.confirmPassword()))
            throw new PasswordMismatchException();

        userRepository.updatePassword(id, dto.newPassword());
    }

    public void logInUser(String userId, String strPassword, Connection con, JFrame parentFrame) {
        // 로그인 로직을 여기에 작성합니다.
        // 예를 들어, 데이터베이스와의 연결을 통해 사용자 인증을 수행할 수 있습니다.
    }
}
