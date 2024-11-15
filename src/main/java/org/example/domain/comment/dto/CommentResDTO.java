package org.example.domain.comment.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.util.converter.TimeConverter.convertToString;

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

        public Detail withChildComments(List<Detail> childComments) {
            return new Detail(id, content, likeCount, createdAt, profileImg, writer, isVerified, childComments);
        }
    }
}
