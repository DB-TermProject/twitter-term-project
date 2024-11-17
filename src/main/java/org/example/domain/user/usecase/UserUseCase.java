package org.example.domain.user.usecase;

import org.example.domain.user.dto.UserReqDTO;
import org.example.domain.user.dto.UserReqDTO.Login;
import org.example.domain.user.dto.UserReqDTO.SignUp;
import org.example.domain.user.dto.UserResDTO;
import org.example.domain.user.service.UserService;
import org.example.util.TransactionManager;
import org.example.util.exception.PasswordMismatchException;
import org.example.util.exception.UserExistsException;

import static org.example.domain.user.dto.UserResDTO.*;

public class UserUseCase {

    private final TransactionManager transactionManager = new TransactionManager();
    private final UserService userService = new UserService();

    public LoginResponse login(Login dto) {
        return transactionManager.executeReadOnly(connection ->
            new LoginResponse(userService.findId(dto, connection))
        );
    }

    public void signUp(SignUp dto) {
        transactionManager.execute(connection -> {
            dto.validate();

            if (userService.existsByEmail(dto.email(), connection))
                throw new UserExistsException();

            userService.save(dto, connection);   // 회원가입 후 로그인 페이지로 리다이렉트
        });
    }

    public ProfileUpdateResponse updateProfile(Long userId, UserReqDTO.Profile dto) {
        return transactionManager.execute(connection -> {
            dto.validate();
            return userService.updateProfile(userId, dto, connection);
        });
    }

    public void updatePassword(Long userId, UserReqDTO.Password dto) {
        transactionManager.execute(connection -> {
            dto.validate();

            if(dto.passwordMatches())    // DTO method
                throw new PasswordMismatchException();

            userService.updatePassword(userId, dto, connection);
        });
    }

    public Profile readProfile(Long userId) {
        return transactionManager.executeReadOnly(connection ->
                userService.findProfile(userId, connection)
        );
    }
}
