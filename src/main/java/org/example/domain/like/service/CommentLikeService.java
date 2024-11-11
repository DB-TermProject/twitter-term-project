package org.example.domain.like.service;

import org.example.domain.comment.service.CommentService;
import org.example.domain.like.repository.CommentLikeRepository;
import org.example.domain.like.repository.LikeRepository;

public class CommentLikeService implements LikeService {

    private final LikeRepository likeRepository = new CommentLikeRepository();
    private final CommentService commentService = new CommentService();

    @Override
    public void save(Long userId, Long commentId) {
        likeRepository.save(userId, commentId);
        commentService.updateLikeCount(commentId, 1L);
    }
}
