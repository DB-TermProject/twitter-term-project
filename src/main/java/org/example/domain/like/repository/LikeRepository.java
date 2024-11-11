package org.example.domain.like.repository;

public interface LikeRepository {

    void save(Long userId, Long id);

    void delete(Long userId, Long id);
}
