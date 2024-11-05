package org.example.domain.follow.dto;

public class FollowReqDTO {

    public record Follow(
            Long from,
            Long to
    ) {}
}
