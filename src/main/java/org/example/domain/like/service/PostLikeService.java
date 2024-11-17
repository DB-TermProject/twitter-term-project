package org.example.domain.like.service;

import org.example.domain.like.repository.LikeRepository;
import org.example.domain.like.repository.PostLikeRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class PostLikeService implements LikeService {

    private final LikeRepository likeRepository = new PostLikeRepository();

    @Override
    public void save(Long userId, Long postId, Connection connection) throws SQLException {
        likeRepository.save(userId, postId, connection);
    }

    @Override
    public void delete(Long userId, Long postId, Connection connection) throws SQLException {
        likeRepository.delete(userId, postId, connection);
    }
}
