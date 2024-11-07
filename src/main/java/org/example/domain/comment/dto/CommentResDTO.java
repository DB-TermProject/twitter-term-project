package org.example.domain.comment.dto;

import java.util.List;

public class CommentResDTO {

    public record Detail(
            Long id,
            String content,
            Long likeCount,
            Long commentCount,
            String createdAt,

            String profileImg,
            String writer,
            Boolean isVerified,

            List<Detail> childComments
    ) {}
}
