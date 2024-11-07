package org.example.domain.user.dto;

public class UserReqDTO {

    public record Login(
            String email,
            String password
    ) {}

    public record SignUp(
            String email,
            String password,
            String name
    ) {}

    public record Profile(
            String  info,
            String profile_image_url,
            String organization,
            Boolean is_public
    ) {}

    public record Password(
            String newPassword,
            String confirmPassword
    ) {}
}
