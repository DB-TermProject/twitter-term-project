package org.example.domain.block.service;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.dto.BlockResDTO.BlockSummary;
import org.example.domain.block.repository.BlockRepository;
import org.example.domain.follow.dto.FollowReqDTO.Follow;
import org.example.domain.follow.repository.FollowRepository;

import java.util.List;

public class BlockService {

    private final BlockRepository blockRepository = new BlockRepository();
    private final FollowRepository followRepository = new FollowRepository();

    public void block(Block dto) {
        Follow follow = new Follow(dto.from(), dto.to());
        if(followRepository.alreadyFollow(follow))
            followRepository.unfollow(follow);
        blockRepository.block(dto);
    }

    public void unblock(Block dto) {
        blockRepository.unblock(dto);
    }

    public List<BlockSummary> findBlocks(Long id) {
        return blockRepository.findBlocks(id);
    }
}
