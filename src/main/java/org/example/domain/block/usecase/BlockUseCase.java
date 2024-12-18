package org.example.domain.block.usecase;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.service.BlockService;
import org.example.domain.follow.service.FollowService;
import org.example.domain.user.service.UserService;
import org.example.util.advice.TransactionManager;

import java.util.List;

import static org.example.domain.block.dto.BlockResDTO.BlockSummary;
import static org.example.domain.follow.dto.FollowReqDTO.Follow;

public class BlockUseCase {

    private final BlockService blockService = new BlockService();
    private final FollowService followService = new FollowService();
    private final UserService userService = new UserService();
    private final TransactionManager transactionManager = new TransactionManager();

    public void block(Block dto) {
        transactionManager.execute(connection -> {
            Follow follow = new Follow(dto.from(), dto.to());
            if(followService.alreadyFollowed(follow, connection)) {
                followService.delete(follow, connection);
                userService.updateFollowCount(dto.from(), dto.to(), -1L, connection);
            }

            blockService.save(dto, connection);
        });
    }

    public void unblock(Block dto) {
        transactionManager.execute(connection -> {
            blockService.delete(dto, connection);
        });
    }

    public List<BlockSummary> findMyBlocks(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            blockService.read(userId, connection)
        );
    }
}
