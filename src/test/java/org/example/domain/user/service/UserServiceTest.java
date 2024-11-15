package org.example.domain.user.service;

import org.example.domain.user.dto.UserReqDTO;
import org.example.domain.user.dto.UserReqDTO.Login;
import org.example.domain.user.dto.UserResDTO;
import org.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void 회원가입() {
        UserReqDTO.SignUp signUpDto = new UserReqDTO.SignUp("root", "1234", "김경규");
        when(userRepository.existsByEmail(signUpDto.email())).thenReturn(false);

        userService.signUp(signUpDto);
    }

    @Test
    void 로그인() {
        Login loginDto = new Login("root", "1234");

        when(userRepository.findIdByEmailAndPassword(loginDto.email(), loginDto.password()))
                .thenReturn(Optional.of(1L));

        UserResDTO.LoginResponse response = userService.login(loginDto);

        System.out.println(response.id());
    }

    @Test
    @Order(3)
    void 프로필_수정() {
        Long userId = 3L;
        UserReqDTO.Profile profileDto = new UserReqDTO.Profile("New Info", "image_url", "Org", false);

        UserResDTO.ProfileUpdateResponse response = userService.updateProfile(userId, profileDto);

        assertEquals(profileDto.info(), response.info());
        assertEquals(profileDto.profile_image_url(), response.profile_image_url());
        assertEquals(profileDto.organization(), response.organization());
        assertEquals(profileDto.is_public(), response.is_public());
    }

    @Test
    @Order(4)
    void 비밀번호_변경() {
        Long userId = 3L;
        UserReqDTO.Password passwordDto = new UserReqDTO.Password("qwer1234", "qwer1234");

        userService.updatePassword(userId, passwordDto);
    }

    @Test
    @Order(5)
    void 내_프로필_조회() {
        Long userId = 1L;
        System.out.println(userService.read(userId));
    }
}