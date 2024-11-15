package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import org.example.domain.follow.repository.FollowRepository;
import org.example.domain.notice.enums.NoticeMessage;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.user.service.UserService;

import java.util.List;

import static org.example.domain.notice.enums.NoticeMessage.*;

public class FollowService {

    private final FollowRepository followRepository = new FollowRepository();
    private final UserService userService = new UserService();
    private final NoticeService noticeService = new NoticeService();

    public void follow(Follow dto) {
        dto.validate();
        if(userService.isPublic(dto.to())) {
            followRepository.follow(dto, "ACCEPTED");
            userService.updateFollowCount(dto.from(), dto.to(), 1L);
            noticeService.notice(dto.to(), FOLLOWING.getMessage(userService.findName(dto.from())));
        } else {
            followRepository.follow(dto, "PENDING");
            noticeService.notice(dto.to(), RECEIVED_FOLLOW_REQUEST.getMessage(userService.findName(dto.from())));
        }
    }

    public void unfollow(Follow dto) {
        followRepository.unfollow(dto);
        userService.updateFollowCount(dto.from(), dto.to(), -1L);
    }

    public boolean alreadyFollowed(Follow dto) {
        return followRepository.alreadyFollowed(dto);
    }

    public void accept(Follow dto) {    // from: 팔로우 보낸 유저, to: 팔로우 받은 유저
        dto.validate();
        followRepository.accept(dto);
        noticeService.notice(dto.from(), ACCEPTED_FOLLOW_REQUEST.getMessage(userService.findName(dto.to())));
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
