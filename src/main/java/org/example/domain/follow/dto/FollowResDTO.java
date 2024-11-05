package org.example.domain.follow.dto;

public class FollowResDTO {

    public record FollowSummary(
            Long id,
            String name,
            String organization,
            String profileImg,
            Boolean isVerified
    ) {}
}
