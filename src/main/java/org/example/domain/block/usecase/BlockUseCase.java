package org.example.domain.block.usecase;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.service.BlockService;
import org.example.domain.follow.service.FollowService;
import org.example.util.TransactionManager;

import java.util.List;

import static org.example.domain.block.dto.BlockResDTO.BlockSummary;
import static org.example.domain.follow.dto.FollowReqDTO.Follow;

public class BlockUseCase {

    private final BlockService blockService = new BlockService();
    private final FollowService followService = new FollowService();
    private final TransactionManager transactionManager = new TransactionManager();

    public void block(Block dto) {
        transactionManager.execute(connection -> {
            Follow follow = new Follow(dto.from(), dto.to());
            if(followService.alreadyFollowed(follow))
                followService.unfollow(follow);

            blockService.save(dto, connection);
            return null;
        });
    }

    public void unblock(Block dto) {
        transactionManager.execute(connection -> {
            blockService.delete(dto, connection);
            return null;
        });
    }

    public List<BlockSummary> findMyBlocks(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            blockService.read(userId, connection)
        );
    }
}
