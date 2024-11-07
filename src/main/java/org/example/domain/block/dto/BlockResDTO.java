package org.example.domain.block.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockResDTO {

    public record BlockSummary(
            Long id,
            String name,
            String organization,
            String profileImg,
            Boolean isVerified
    ) {
        public static BlockSummary toBlockSummary(ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String organization = resultSet.getString("organization");
            String profileImageUrl = resultSet.getString("profile_image_url");
            Boolean isVerified = resultSet.getBoolean("is_verified");

            return new BlockSummary(id, name, organization, profileImageUrl, isVerified);
        }
    }
}
