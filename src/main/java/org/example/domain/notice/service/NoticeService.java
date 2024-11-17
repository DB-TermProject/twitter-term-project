package org.example.domain.notice.service;

import org.example.domain.notice.dto.NoticeResDTO.Detail;
import org.example.domain.notice.repository.NoticeRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NoticeService {

    private final NoticeRepository noticeRepository = new NoticeRepository();

    public void notice(Long userId, String message, Connection connection) throws SQLException {
        noticeRepository.save(userId, message, connection);
    }

    public List<Detail> read(Long userId, Connection connection) throws SQLException {
        return noticeRepository.read(userId, connection);
    }
}
