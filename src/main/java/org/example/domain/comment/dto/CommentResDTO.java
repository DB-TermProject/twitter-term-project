package org.example.domain.comment.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class CommentResDTO {

    public record Detail(
            Long id,
            String content,
            Long likeCount,
            String createdAt,

            String profileImg,
            String writer,
            Boolean isVerified,

            List<Detail> childComments
    ) {
        public static CommentResDTO.Detail toDetail(ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String content = resultSet.getString("content");
            Long likeCount = resultSet.getLong("like_count");
            String createdAt = convertToString(resultSet.getTimestamp("created_at"));
            String writer = resultSet.getString("name");
            String profileImg = resultSet.getString("profile_image_url");
            Boolean isVerified = resultSet.getBoolean("is_verified");

            return new CommentResDTO.Detail(id, content, likeCount, createdAt, profileImg, writer, isVerified, List.of());
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

        public Detail withChildComments(List<Detail> childComments) {
            return new Detail(id, content, likeCount, createdAt, profileImg, writer, isVerified, childComments);
        }
    }
}
