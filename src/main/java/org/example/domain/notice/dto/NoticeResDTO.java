package org.example.domain.notice.dto;

import org.example.domain.comment.dto.CommentResDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.util.converter.TimeConverter.convertToString;

public class NoticeResDTO {

    public record Detail(
            Long id,
            String content,
            String createdAt
    ) {
        public static Detail toDetail(ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String content = resultSet.getString("content");
            String createdAt = convertToString(resultSet.getTimestamp("created_at"));

            return new Detail(id, content, createdAt);
        }
    }
}
