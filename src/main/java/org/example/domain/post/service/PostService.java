package org.example.domain.post.service;

import org.example.domain.post.dto.PostReqDTO.Save;
import org.example.domain.post.dto.PostResDTO.PostSummary;
import org.example.domain.post.repository.PostRepository;

import java.util.List;

public class PostService {

    private final PostRepository postRepository = new PostRepository();

    public void save(Long id, Save dto) {
        dto.validate();
        postRepository.save(id, dto);
    }

    public List<PostSummary> findHomeFeed(Long id) {
        return postRepository.findHomeFeed(id);
    }
}
