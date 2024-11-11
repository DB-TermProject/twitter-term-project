package org.example.domain.post.service;

import org.example.domain.post.dto.PostReqDTO.Save;
import org.example.domain.post.dto.PostResDTO.Detail;
import org.example.domain.post.repository.PostRepository;

import java.util.List;

public class PostService {

    private final PostRepository postRepository = new PostRepository();

    public void save(Long id, Save dto) {
        dto.validate();
        postRepository.save(id, dto);
    }

    public List<Detail> findHomeFeed(Long userId) {     // 나 + 팔로잉
        return postRepository.findHomeFeed(userId);
    }

    public List<Detail> findUserFeed(Long userId) {     // Only 나
        return postRepository.findUserFeed(userId);
    }

    public Detail findPost(Long postId) {
        return postRepository.findPost(postId);
    }

    public void updateCommentCount(Long postId, Long value) {
        postRepository.updateCommentCount(postId, value);
    }
}
