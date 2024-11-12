package org.example.domain.post.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.example.util.converter.TimeConverter.convertToString;

public class PostResDTO {

    public record Detail(
            Long id,
            String content,
            Long likeCount,
            Long commentCount,
            String createdAt,

            String profileImg,
            String writer,
            Boolean isVerified
    ) {
        public static Detail toDetail(ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String content = resultSet.getString("content");
            Long likeCount = resultSet.getLong("like_count");
            Long commentCount = resultSet.getLong("comment_count");
            String createdAt = convertToString(resultSet.getTimestamp("created_at"));
            String writer = resultSet.getString("name");
            String profileImg = resultSet.getString("profile_image_url");
            Boolean isVerified = resultSet.getBoolean("is_verified");

            return new Detail(id, content, likeCount, commentCount, createdAt, profileImg, writer, isVerified);
        }
    }
}
