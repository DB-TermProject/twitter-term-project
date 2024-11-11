package org.example.domain.like.service;

import org.example.domain.like.repository.CommentLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommentLikeServiceTest {

    private CommentLikeRepository commentLikeRepository;
    private CommentLikeService commentLikeService;

    @BeforeEach
    void setUp() {
        commentLikeRepository = Mockito.mock(CommentLikeRepository.class);
        commentLikeService = new CommentLikeService();
    }

    @Test
    void 댓글_좋아요() {
        // given
        Long userId = 1L;
        Long commentId = 7L;

        // when
        commentLikeService.save(userId, commentId);
    }
}
