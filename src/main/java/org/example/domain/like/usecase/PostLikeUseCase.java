package org.example.domain.like.usecase;

import org.example.domain.like.service.LikeService;
import org.example.domain.like.service.PostLikeService;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.post.service.PostService;
import org.example.domain.user.service.UserService;
import org.example.util.advice.TransactionManager;

import static org.example.domain.notice.enums.NoticeMessage.LIKE_ON_POST;

public class PostLikeUseCase {

    private final TransactionManager transactionManager = new TransactionManager();
    private final LikeService likeService = new PostLikeService();
    private final PostService postService = new PostService();
    private final NoticeService noticeService = new NoticeService();
    private final UserService userService = new UserService();

    public void like(Long userId, Long postId) {
        transactionManager.execute(connection -> {
            likeService.save(userId, postId, connection);
            postService.updateLikeCount(postId, 1L);
            noticeService.notice(postService.findWriter(postId), LIKE_ON_POST.getMessage(userService.findName(userId, connection)), connection);
        });
    }

    public void unlike(Long userId, Long postId) {
        transactionManager.execute(connection -> {
            likeService.delete(userId, postId, connection);
            postService.updateLikeCount(postId, -1L);
        });
    }
}
