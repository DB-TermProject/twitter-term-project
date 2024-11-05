package org.example.domain.follow.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class FollowServiceTest {

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
}
