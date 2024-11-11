package org.example.domain.block.service;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.dto.BlockResDTO.BlockSummary;
import org.example.domain.block.repository.BlockRepository;
import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.service.FollowService;

import java.util.List;

public class BlockService {

    private final BlockRepository blockRepository = new BlockRepository();
    private final FollowService followService = new FollowService();

    public void block(Block dto) {
        Follow follow = new Follow(dto.from(), dto.to());
        if(followService.alreadyFollowed(follow))
            followService.unfollow(follow);

        blockRepository.block(dto);
    }

    public void unblock(Block dto) {
        blockRepository.unblock(dto);
    }

    public List<BlockSummary> findBlocks(Long id) {
        return blockRepository.findBlocks(id);
    }
}