package org.example.domain.user.dto;

public class UserResDTO {

    public record LoginResponse(
            Long id
    ) {}

    public record ProfileResponse(
            String info,
            String profile_image_url,
            String organization,
            Boolean is_public
    ) {}
}
