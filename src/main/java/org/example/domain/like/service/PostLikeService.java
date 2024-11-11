package org.example.domain.like.service;

import org.example.domain.like.repository.LikeRepository;
import org.example.domain.like.repository.PostLikeRepository;
import org.example.domain.post.service.PostService;

public class PostLikeService implements LikeService {

    private final LikeRepository likeRepository = new PostLikeRepository();
    private final PostService postService = new PostService();

    @Override
    public void save(Long userId, Long postId) {
        likeRepository.save(userId, postId);
        postService.updateLikeCount(postId, 1L);
    }
}
