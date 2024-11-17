package org.example.domain.block.usecase;

import org.example.domain.block.dto.BlockReqDTO;
import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.service.BlockService;
import org.example.domain.follow.service.FollowService;
import org.example.util.config.JdbcConfig;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.domain.follow.dto.FollowReqDTO.Follow;

public class BlockUseCase {

    private final BlockService blockService = new BlockService();
    private final FollowService followService = new FollowService();

    public void block(Block dto) {
        try (Connection connection = JdbcConfig.getConnection()) {
            try {
                Follow follow = new Follow(dto.from(), dto.to());
                if(followService.alreadyFollowed(follow))
                    followService.unfollow(follow);

                blockService.save(dto);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void unblock(Block dto) {
        blockService.delete(dto);
    }

    public void findMyBlocks(Long userId) {
        blockService.read(userId);
    }
}
