package org.example.domain.post.dto;

import org.example.util.exception.EmptyContentException;

public class PostReqDTO {

    public record Save(
        String content
    ) {
        public void validate() {
            if(content == null || content.isEmpty())
                throw new EmptyContentException();
        }
    }
}
