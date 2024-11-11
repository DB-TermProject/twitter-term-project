package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.repository.CommentRepository;

import java.util.List;

import static org.example.domain.comment.dto.CommentResDTO.Detail;

public class CommentService {

    private final CommentRepository commentRepository = new CommentRepository();

    public void save(Long userId, Save dto) {
        if(dto.parentCommentId() == null) {
            commentRepository.save(userId, dto.content(), dto.postId());
        } else {
            commentRepository.save(userId, dto);
        }
    }

    public List<Detail> findComments(Long postId) {
        return commentRepository.findComments(postId);
    }
}
