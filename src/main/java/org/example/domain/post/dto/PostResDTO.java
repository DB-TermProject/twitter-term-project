package org.example.domain.post.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

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

        private static String convertToString(Timestamp time) {
            LocalDateTime createdAt = time.toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            Duration duration = Duration.between(createdAt, now);

            if (duration.toMinutes() < 1) {
                return "방금 전";
            } else if (duration.toMinutes() < 60) {
                return duration.toMinutes() + "분 전";
            } else if (duration.toHours() < 24) {
                return duration.toHours() + "시간 전";
            } else if (duration.toDays() < 7) {
                return duration.toDays() + "일 전";
            } else {
                return createdAt.toLocalDate().toString(); // 예: "2024-11-05"
            }
        }
    }
}
