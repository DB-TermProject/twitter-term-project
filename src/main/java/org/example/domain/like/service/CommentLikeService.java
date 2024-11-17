package org.example.domain.like.service;

import org.example.domain.like.repository.CommentLikeRepository;
import org.example.domain.like.repository.LikeRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class CommentLikeService implements LikeService {

    private final LikeRepository likeRepository = new CommentLikeRepository();

    @Override
    public void save(Long userId, Long commentId, Connection connection) throws SQLException {
        likeRepository.save(userId, commentId, connection);
    }

    @Override
    public void delete(Long userId, Long commentId, Connection connection) throws SQLException {
        likeRepository.delete(userId, commentId, connection);
    }
}
