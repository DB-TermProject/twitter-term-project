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

    public boolean alreadyFollowed(Follow dto) {
        return followRepository.alreadyFollow(dto);
    }

    public void accept(Follow dto) {
        dto.validate();
        followRepository.accept(dto);
    }

    public List<FollowSummary> findFollowers(Long id) {
        return followRepository.findFollowers(id, "ACCEPTED");
    }

    public List<FollowSummary> findFollowings(Long id) {
        return followRepository.findFollowings(id, "ACCEPTED");
    }

    public List<FollowSummary> findFollowingRequests(Long id) {     // 나의 신청한 팔로우 요청 목록
        return followRepository.findFollowers(id, "PENDING");
    }

    public List<FollowSummary> findFollowersRequests(Long id) {     // 내가 받은 팔로우 요청 목록
        return followRepository.findFollowers(id, "PENDING");
    }
}
