package org.example.domain.like.service;

import org.example.domain.like.repository.LikeRepository;
import org.example.domain.like.repository.PostLikeRepository;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.post.service.PostService;
import org.example.domain.user.service.UserService;

import static org.example.domain.notice.enums.NoticeMessage.LIKE_ON_POST;

public class PostLikeService implements LikeService {

    private final LikeRepository likeRepository = new PostLikeRepository();
    private final PostService postService = new PostService();
    private final NoticeService noticeService = new NoticeService();
    private final UserService userService = new UserService();

    @Override
    public void like(Long userId, Long postId) {
        likeRepository.save(userId, postId);
        postService.updateLikeCount(postId, 1L);
        noticeService.notice(postService.findWriter(postId), LIKE_ON_POST.getMessage(userService.findName(userId)));
    }

    @Override
    public void unlike(Long userId, Long postId) {
        likeRepository.delete(userId, postId);
        postService.updateLikeCount(postId, -1L);
    }
}
