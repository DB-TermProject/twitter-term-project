package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.dto.CommentReqDTO.Update;
import org.example.domain.comment.repository.CommentRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.example.domain.comment.dto.CommentResDTO.Detail;

public class CommentService {

    private final CommentRepository commentRepository = new CommentRepository();

    public void save(Long userId, Save dto, Connection connection) throws SQLException {
        commentRepository.save(userId, dto, connection);
    }

    public Long findWriter(Long commentId, Connection connection) throws SQLException {
        return commentRepository.findWriter(commentId, connection);
    }

    public List<Detail> read(Long postId, Connection connection) throws SQLException {
        return commentRepository.findComments(postId, connection);
    }

    public void update(Long commentId, Update dto, Connection connection) throws SQLException {
        commentRepository.updateComment(commentId, dto, connection);
    }

    public Long delete(Long commentId, Connection connection) throws SQLException {
        return commentRepository.delete(commentId, connection);
    }

    public void updateLikeCount(Long commentId, Long value, Connection connection) throws SQLException {
        commentRepository.updateLikeCount(commentId, value, connection);
    }
}
