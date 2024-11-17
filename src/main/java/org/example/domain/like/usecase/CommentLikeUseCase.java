package org.example.domain.like.usecase;

import org.example.domain.comment.service.CommentService;
import org.example.domain.like.service.CommentLikeService;
import org.example.domain.like.service.LikeService;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.user.service.UserService;
import org.example.util.TransactionManager;

import static org.example.domain.notice.enums.NoticeMessage.LIKE_ON_COMMENT;

public class CommentLikeUseCase {

    private final TransactionManager transactionManager = new TransactionManager();
    private final LikeService likeService = new CommentLikeService();
    private final CommentService commentService = new CommentService();
    private final NoticeService noticeService = new NoticeService();
    private final UserService userService = new UserService();

    public void like(Long userId, Long commentId) {
        transactionManager.execute(connection -> {
            likeService.save(userId, commentId, connection);
            commentService.updateLikeCount(commentId, 1L, connection);
            noticeService.notice(commentService.findWriter(commentId, connection), LIKE_ON_COMMENT.getMessage(userService.findName(userId)));
        });
    }

    public void unlike(Long userId, Long commentId) {
        transactionManager.execute(connection -> {
            likeService.delete(userId, commentId, connection);
            commentService.updateLikeCount(commentId, -1L, connection);
        });
    }
}
