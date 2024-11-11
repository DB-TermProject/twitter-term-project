package org.example.domain.block.service;

import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.repository.FollowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private BlockService blockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 팔로우중인_유저_차단() {
        Block blockDto = new Block(9L, 10L);
        Follow follow = new Follow(blockDto.from(), blockDto.to());

        when(followRepository.alreadyFollow(follow)).thenReturn(true);

        blockService.block(blockDto);
    }

    @Test
    void 차단() {
        Block blockDto = new Block(9L, 10L);
        Follow follow = new Follow(blockDto.from(), blockDto.to());

        when(followRepository.alreadyFollow(follow)).thenReturn(false);

        blockService.block(blockDto);
    }

    @Test
    void 차단_해제() {
        Block blockDto = new Block(9L, 10L);
        blockService.unblock(blockDto);
    }

    @Test
    void 차단_목록_조회() {
        Long userId = 9L;
        BlockSummary summary = new BlockSummary(userId, "Blocked User", "org", "profileImg", true);
        when(blockRepository.findBlocks(userId)).thenReturn(Collections.singletonList(summary));

        List<BlockSummary> result = blockService.findBlocks(userId);

        result.listIterator().forEachRemaining(System.out::println);
    }
}
