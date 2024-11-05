package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import org.example.domain.follow.repository.FollowRepository;
import org.example.domain.user.repository.UserRepository;

import java.util.List;

public class FollowService {

    private final FollowRepository followRepository = new FollowRepository();
    private final UserRepository userRepository = new UserRepository();

    public void follow(Follow dto) {
        dto.validate();
        followRepository.follow(dto);
        userRepository.updateFollowingCount(dto.from(), 1L);
        userRepository.updateFollowerCount(dto.to(), 1L);
    }

    public void unfollow(Follow dto) {
        dto.validate();
        followRepository.unfollow(dto);
        userRepository.updateFollowingCount(dto.from(), -1L);
        userRepository.updateFollowerCount(dto.to(), -1L);
    }

    public List<FollowSummary> findFollowers(Long id) {
        return followRepository.findFollowers(id);
    }

    public List<FollowSummary> findFollowings(Long id) {
        return followRepository.findFollowings(id);
    }
}
