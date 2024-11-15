package org.example.domain.block.service;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.dto.BlockResDTO.BlockSummary;
import org.example.domain.block.repository.BlockRepository;

import java.util.List;

public class BlockService {

    private final BlockRepository blockRepository = new BlockRepository();

    public void save(Block dto) {
        blockRepository.block(dto);
    }

    public void delete(Block dto) {
        blockRepository.unblock(dto);
    }

    public List<BlockSummary> read(Long id) {
        return blockRepository.findBlocks(id);
    }
}