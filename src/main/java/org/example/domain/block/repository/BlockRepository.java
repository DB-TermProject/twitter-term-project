package org.example.domain.block.repository;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.domain.block.dto.BlockResDTO.BlockSummary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlockRepository {

    public void block(Connection connection, Block dto) throws SQLException {
        String sql = "INSERT INTO block (user_id, blocked_user_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());
            statement.executeUpdate();
        }
    }

    public void unblock(Connection connection, Block dto) throws SQLException {
        String sql = "DELETE FROM block WHERE user_id = ? AND blocked_user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());
            statement.executeUpdate();
        }
    }

    public List<BlockSummary> findBlocks(Connection connection, Long id) throws SQLException {
        String sql = "SELECT user_id, blocked_user_id FROM block WHERE user_id = ?";

        List<BlockSummary> blocks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    blocks.add(BlockSummary.toBlockSummary(resultSet));
                }
            }
        }
        return blocks;
    }
}
