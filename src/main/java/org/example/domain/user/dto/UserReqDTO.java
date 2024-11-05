package org.example.domain.user.dto;

import org.example.util.exception.InvalidRequestException;

public class UserReqDTO {

    public record Login(
            String email,
            String password
    ) {}

    public record SignUp(
            String email,
            String password,
            String name
    ) {
        public void validate() {
            if(email == null || email.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()) {
                throw new InvalidRequestException();
            }
        }
    }

    public record Profile(
            String info,
            String profile_image_url,
            String organization,
            Boolean is_public
    ) {
        public void validate() {
            if(is_public == null)
                throw new InvalidRequestException();
        }
    }

    public record Password(
            String newPassword,
            String confirmPassword
    ) {
        public void validate() {
            if (newPassword == null || newPassword.isEmpty()) {
                throw new InvalidRequestException();
            }
        }
    }
}
