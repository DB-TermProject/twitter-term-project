package org.example.domain.notice.service;

import org.example.domain.notice.dto.NoticeResDTO.Detail;
import org.example.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class NoticeServiceTest {

    private NoticeRepository noticeRepository;
    private NoticeService noticeService;

    @BeforeEach
    void setUp() {
        noticeRepository = Mockito.mock(NoticeRepository.class);
        noticeService = new NoticeService();
    }

    @Test
    void read_shouldReturnUserNotices() {
        // given
        Long userId = 1L;

        // when
        List<Detail> actualNotices = noticeService.read(userId);

        // then
        actualNotices.listIterator().forEachRemaining(System.out::println);
    }
}
