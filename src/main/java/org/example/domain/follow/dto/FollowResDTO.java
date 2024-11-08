package org.example.domain.follow.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FollowResDTO {

    public record FollowSummary(
            Long id,
            String name,
            String organization,
            String profileImg,
            Boolean isVerified
    ) {
        public static FollowSummary toFollowSummary(ResultSet resultSet) throws SQLException {
            Long followerId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String organization = resultSet.getString("organization");
            String profileImageUrl = resultSet.getString("profile_image_url");
            Boolean isVerified = resultSet.getBoolean("is_verified");

            return new FollowSummary(followerId, name, organization, profileImageUrl, isVerified);
        }
    }
}
