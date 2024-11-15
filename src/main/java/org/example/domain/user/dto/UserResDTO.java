package org.example.domain.user.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResDTO {

    public record LoginResponse(
            Long id
    ) {}

    public record ProfileUpdateResponse(
            String info,
            String profile_image_url,
            String organization,
            Boolean is_public
    ) {}

    public record Profile(
            Long id,
            String email,
            String name,
            String info,
            String profileImg,
            String organization,
            Boolean isVerified,
            Boolean isPublic,
            Long followerCount,
            Long followingCount
    ) {
        public static Profile toProfile(ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String email = resultSet.getString("email");
            String name = resultSet.getString("name");
            String info = resultSet.getString("info");
            String profileImg = resultSet.getString("profile_image_url");
            String organization = resultSet.getString("organization");
            Boolean isVerified = resultSet.getBoolean("is_verified");
            Boolean isPublic = resultSet.getBoolean("is_public");
            Long followerCount = resultSet.getLong("followers_count");
            Long followingCount = resultSet.getLong("following_count");

            return new Profile(id, email, name, info, profileImg, organization, isVerified, isPublic, followerCount, followingCount);
        }
    }
}
