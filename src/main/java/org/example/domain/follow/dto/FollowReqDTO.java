package org.example.domain.follow.dto;

import org.example.util.exception.InvalidRequestException;

public class FollowReqDTO {

    public record Follow(
            Long from,
            Long to
    ) {
        public void validate() {
            if(from == null || to == null)
                throw new InvalidRequestException();
        }
    }
}