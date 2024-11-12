package org.example.domain.notice.enums;

public enum NoticeMessage {
    RECEIVED_FOLLOW_REQUEST("님이 팔로우 요청을 보냈습니다."),
    ACCEPTED_FOLLOW_REQUEST("님이 팔로우 요청을 수락하였습니다."),
    FOLLOWING("님이 팔로우하기 시작했습니다."),
    COMMENT_ON_POST("님이 회원님의 게시물에 댓글을 작성하였습니다."),
    COMMENT_ON_COMMENT("님이 회원님의 댓글에 답글을 작성하였습니다."),
    LIKE_ON_POST("님이 게시물을 좋아합니다."),
    LIKE_ON_COMMENT("님이 댓글을 좋아합니다.");

    private String message;

    NoticeMessage(String message) {
        this.message = message;
    }

    public String getMessage(String prefix) {
        return prefix + message;
    }
}
