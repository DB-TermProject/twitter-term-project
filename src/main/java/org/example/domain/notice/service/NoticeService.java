package org.example.domain.notice.service;

import org.example.domain.notice.repository.NoticeRepository;

public class NoticeService {

    private final NoticeRepository noticeRepository = new NoticeRepository();

    public void notice(Long userId, String message) {
        noticeRepository.save(userId, message);
    }

    // read
}
