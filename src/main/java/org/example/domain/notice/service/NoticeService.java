package org.example.domain.notice.service;

import org.example.domain.notice.dto.NoticeResDTO.Detail;
import org.example.domain.notice.repository.NoticeRepository;

import java.util.List;

public class NoticeService {

    private final NoticeRepository noticeRepository = new NoticeRepository();

    public void notice(Long userId, String message) {
        noticeRepository.save(userId, message);
    }

    public List<Detail> read(Long userId) {
        return noticeRepository.read(userId);
    }
}
