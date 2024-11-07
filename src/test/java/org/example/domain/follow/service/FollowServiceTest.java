package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.dto.FollowResDTO.FollowSummary;
import org.example.domain.follow.repository.FollowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowService followService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 팔로우() {
        // given
        Follow followDto = new Follow(3L, 4L); // Example IDs for `from` and `to`

        // when
        followService.follow(followDto);
    }

    @Test
    void 언팔로우() {
        // given
        Follow followDto = new Follow(3L, 4L); // Example IDs for `from` and `to`

        // when
        followService.unfollow(followDto);
    }

    @Test
    void 팔로잉_목록_조회() {
        // given
        Long userId = 4L;
        List<FollowSummary> expectedFollowings = Arrays.asList(
                new FollowSummary(4L, "Charlie", "OrganizationC", "http://example.com/profile3.jpg", true),
                new FollowSummary(5L, "Diana", "OrganizationD", "http://example.com/profile4.jpg", false)
        );

        // when
        when(followRepository.findFollowings(userId, "ACCEPTED")).thenReturn(expectedFollowings);
        List<FollowSummary> followings = followService.findFollowings(userId);

        // then
        followings.listIterator().forEachRemaining(System.out::println);
    }

    @Test
    void 팔로우_목록_조회() {
        // given
        Long userId = 4L;
        List<FollowSummary> expectedFollowers = Arrays.asList(
                new FollowSummary(2L, "Alice", "OrganizationA", "http://example.com/profile1.jpg", true),
                new FollowSummary(3L, "Bob", "OrganizationB", "http://example.com/profile2.jpg", false)
        );

        // when
        when(followRepository.findFollowers(userId, "ACCEPTED")).thenReturn(expectedFollowers);
        List<FollowSummary> followers = followService.findFollowers(userId);

        // then
        followers.listIterator().forEachRemaining(System.out::println);
    }

    @Test
    void 내가_요청한_팔로우_요청_목록_조회() {
        // given
        Long userId = 3L;
        List<FollowSummary> expectedRequests = Arrays.asList(
                new FollowSummary(2L, "Alice", "OrganizationA", "http://example.com/profile1.jpg", false),
                new FollowSummary(3L, "Bob", "OrganizationB", "http://example.com/profile2.jpg", true)
        );

        // when
        when(followRepository.findFollowers(userId, "PENDING")).thenReturn(expectedRequests);
        List<FollowSummary> requests = followService.findFollowingRequests(userId);

        requests.listIterator().forEachRemaining(System.out::println);
    }

    @Test
    void 내가_받은_팔로우_요청_목록_조회() {
        // given
        Long userId = 4L;
        List<FollowSummary> expectedRequests = Arrays.asList(
                new FollowSummary(2L, "Alice", "OrganizationA", "http://example.com/profile1.jpg", false),
                new FollowSummary(3L, "Bob", "OrganizationB", "http://example.com/profile2.jpg", true)
        );

        // when
        when(followRepository.findFollowers(userId, "PENDING")).thenReturn(expectedRequests);
        List<FollowSummary> requests = followService.findFollowingRequests(userId);

        requests.listIterator().forEachRemaining(System.out::println);
    }
}
