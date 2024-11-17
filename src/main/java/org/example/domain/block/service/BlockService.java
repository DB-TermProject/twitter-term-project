package org.example.domain.block.service;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.dto.BlockResDTO.BlockSummary;
import org.example.domain.block.repository.BlockRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BlockService {

    private final BlockRepository blockRepository = new BlockRepository();

    public void save(Block dto, Connection connection) throws SQLException {
        blockRepository.block(connection, dto);
    }

    public void delete(Block dto, Connection connection) throws SQLException {
        blockRepository.unblock(connection, dto);
    }

    public List<BlockSummary> read(Long id, Connection connection) throws SQLException {
        return blockRepository.findBlocks(connection, id);
    }
}