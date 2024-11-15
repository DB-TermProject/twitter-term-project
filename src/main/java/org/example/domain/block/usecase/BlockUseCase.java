package org.example.domain.block.usecase;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.service.BlockService;
import org.example.domain.follow.dto.FollowReqDTO;
import org.example.domain.follow.service.FollowService;

public class BlockUseCase {

    private final BlockService blockService = new BlockService();
    private final FollowService followService = new FollowService();

    public void block(Block dto) {
        FollowReqDTO.Follow follow = new FollowReqDTO.Follow(dto.from(), dto.to());
        if(followService.alreadyFollowed(follow))
            followService.unfollow(follow);

        blockService.save(dto);
    }
}
