package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.repository.CommentRepository;

public class CommentService {

    private final CommentRepository commentRepository = new CommentRepository();

    public void save(Long id, Save dto) {
        if(dto.parentCommentId() == null) {
            commentRepository.save(id, dto.content(), dto.postId());
        } else {
            commentRepository.save(id, dto);
        }
    }
}
