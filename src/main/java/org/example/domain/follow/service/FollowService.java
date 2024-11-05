package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.repository.FollowRepository;
import org.example.domain.user.repository.UserRepository;

public class FollowService {

    private final FollowRepository followRepository = new FollowRepository();
    private final UserRepository userRepository = new UserRepository();

    public void follow(Follow dto) {
        followRepository.follow(dto);
        userRepository.updateFollowingCount(dto.from(), 1L);
        userRepository.updateFollowerCount(dto.to(), 1L);
    }

    public void unfollow(Follow dto) {
        followRepository.unfollow(dto);
        userRepository.updateFollowingCount(dto.from(), -1L);
        userRepository.updateFollowerCount(dto.to(), -1L);
    }
}
