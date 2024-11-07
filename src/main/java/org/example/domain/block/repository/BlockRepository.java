package org.example.domain.block.repository;

import org.example.domain.block.dto.BlockReqDTO.Block;
import org.example.util.config.JdbcConfig;
import org.example.util.exception.SqlExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.block.dto.BlockResDTO.BlockSummary;
import static org.example.domain.block.dto.BlockResDTO.BlockSummary.toBlockSummary;

public class BlockRepository {

    public void block(Block dto) {
        String sql = "INSERT INTO block (user_id, blocked_user_id) VALUES (?, ?)";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SqlExecutionException();
        }
    }

    public void unblock(Block dto) {
        String sql = "DELETE FROM block WHERE user_id = ? AND blocked_user_id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, dto.from());
            statement.setLong(2, dto.to());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }

    public List<BlockSummary> findBlocks(Long id) {
        String sql = "SELECT u.id, u.name, u.organization, u.profile_image_url, u.is_verified " +
                "FROM block b JOIN user u ON b.blocked_user_id = u.id WHERE b.user_id = ?";

        try (Connection connection = JdbcConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            List<BlockSummary> blocks = new ArrayList<>();
            while (resultSet.next()) {
                blocks.add(toBlockSummary(resultSet));
            }

            return blocks;
        } catch (SQLException e) {
            throw new SqlExecutionException();
        }
    }
}
