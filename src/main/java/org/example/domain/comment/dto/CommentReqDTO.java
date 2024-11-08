package org.example.domain.comment.dto;

public class CommentReqDTO {

    public record Save(
            String content,
            Long postId,
            Long parentCommentId
    ) {}
}
