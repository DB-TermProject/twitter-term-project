package org.example.domain.like.service;

import org.example.domain.comment.service.CommentService;
import org.example.domain.like.repository.CommentLikeRepository;
import org.example.domain.like.repository.LikeRepository;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.user.service.UserService;

import static org.example.domain.notice.enums.NoticeMessage.LIKE_ON_COMMENT;

public class CommentLikeService implements LikeService {

    private final LikeRepository likeRepository = new CommentLikeRepository();
    private final CommentService commentService = new CommentService();
    private final NoticeService noticeService = new NoticeService();
    private final UserService userService = new UserService();

    @Override
    public void like(Long userId, Long commentId) {
        likeRepository.save(userId, commentId);
        commentService.updateLikeCount(commentId, 1L);
        noticeService.notice(commentService.findWriter(commentId), LIKE_ON_COMMENT.getMessage(userService.findName(userId)));
    }

    @Override
    public void unlike(Long userId, Long commentId) {
        likeRepository.delete(userId, commentId);
        commentService.updateLikeCount(commentId, -1L);
    }
}
