package org.example.domain.post.service;

import org.example.domain.post.dto.PostReqDTO.Save;
import org.example.domain.post.dto.PostResDTO.Detail;
import org.example.domain.post.repository.PostRepository;

import java.sql.Connection;
import java.util.List;

public class PostService {

    private final PostRepository postRepository = new PostRepository();

    public void save(Long id, Save dto, Connection connection) {
        dto.validate();
        postRepository.save(id, dto, connection);
    }

    public List<Detail> findHomeFeed(Long userId, Connection connection) {     // 나 + 팔로잉
        return postRepository.findHomeFeed(userId, connection);
    }

    public List<Detail> findUserFeed(Long userId) {     // Only 나
        return postRepository.findUserFeed(userId);
    }

    public Detail findPost(Long postId, Connection connection) {
        return postRepository.findPost(postId, connection);
    }

    public void updateCommentCount(Long postId, Long value) {
        postRepository.updateCommentCount(postId, value);
    }

    public void updateLikeCount(Long postId, Long value) {
        postRepository.updateLikeCount(postId, value);
    }

    public Long findWriter(Long postId) {
        return postRepository.findWriter(postId);
    }
}
