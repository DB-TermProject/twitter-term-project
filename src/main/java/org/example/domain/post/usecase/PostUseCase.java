package org.example.domain.post.usecase;

import org.example.domain.post.dto.PostResDTO.Detail;
import org.example.domain.post.service.PostService;
import org.example.util.advice.TransactionManager;

import java.util.List;

import static org.example.domain.post.dto.PostReqDTO.Save;

public class PostUseCase {

    private final TransactionManager transactionManager = new TransactionManager();
    private final PostService postService = new PostService();

    public void write(Long userId, Save dto) {
        transactionManager.execute(connection -> {
            dto.validate();
            postService.save(userId, dto, connection);
        });
    }

    public List<Detail> readHomeFeed(Long userId) {
        return transactionManager.executeReadOnly(connection ->
            postService.findHomeFeed(userId, connection)
        );
    }

    public List<Detail> readUserFeed(Long userId) {
        return transactionManager.executeReadOnly(connection ->
                postService.findUserFeed(userId)
        );
    }

    public Detail readPost(Long postId) {
        return transactionManager.executeReadOnly(connection ->
                postService.findPost(postId, connection)
        );
    }
}
