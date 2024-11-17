package org.example.domain.notice.repository;

import org.example.domain.notice.dto.NoticeResDTO.Detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.domain.notice.dto.NoticeResDTO.Detail.toDetail;

public class NoticeRepository {

    public void save(Long id, String message, Connection connection) throws SQLException {
        String sql = "INSERT INTO notice (content, user_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, message);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    public List<Detail> read(Long id, Connection connection) throws SQLException {
        String sql = """
            SELECT n.id, n.content,
                   CASE
                       WHEN n.updated_at IS NOT NULL THEN n.updated_at 
                       ELSE n.created_at 
                   END AS created_at
            FROM notice n        
            WHERE n.user_id = ?
            ORDER BY created_at DESC
            LIMIT 50
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            List<Detail> notices = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                notices.add(toDetail(resultSet));
            }

            return notices;
        }
    }
}
