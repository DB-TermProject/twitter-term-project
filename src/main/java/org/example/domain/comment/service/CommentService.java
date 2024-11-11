package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.dto.CommentReqDTO.Update;
import org.example.domain.comment.repository.CommentRepository;
import org.example.domain.post.service.PostService;

import java.util.List;

import static org.example.domain.comment.dto.CommentResDTO.Detail;

public class CommentService {

    private final CommentRepository commentRepository = new CommentRepository();
    private final PostService postService = new PostService();

    public void save(Long userId, Save dto) {
        if(dto.parentCommentId() == null) {
            commentRepository.save(userId, dto.content(), dto.postId());
        } else {
            commentRepository.save(userId, dto);
        }

        postService.updateCommentCount(dto.postId(), 1L);
    }

    public List<Detail> read(Long postId) {
        return commentRepository.findComments(postId);
    }

    public void update(Long commentId, Update dto) {
        commentRepository.updateComment(commentId, dto);
    }

    public void delete(Long commentId, Long postId) {
        Long deletedComments = commentRepository.delete(commentId);
        postService.updateCommentCount(postId, -1 * deletedComments);
    }

    public void updateLikeCount(Long commentId, Long value) {
        commentRepository.updateLikeCount(commentId, value);
    }
}
