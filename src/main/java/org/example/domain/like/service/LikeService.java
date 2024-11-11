package org.example.domain.like.service;

public interface LikeService {

    void like(Long userId, Long id);

    void unlike(Long userId, Long id);
}
