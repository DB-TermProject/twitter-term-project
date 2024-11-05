package org.example.domain.post.service;

import org.example.domain.post.dto.PostReqDTO.Save;
import org.example.domain.post.repository.PostRepository;

public class PostService {

    private final PostRepository postRepository = new PostRepository();

    public void save(Long id, Save dto) {
        postRepository.save(id, dto);
    }
}
