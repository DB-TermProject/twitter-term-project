package org.example.domain.comment.usecase;

import org.example.domain.comment.dto.CommentReqDTO;
import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.service.CommentService;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.post.service.PostService;
import org.example.domain.user.service.UserService;

import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_COMMENT;
import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_POST;

public class CommentUseCase {

    private final CommentService commentService = new CommentService();
    private final NoticeService noticeService = new NoticeService();
    private final PostService postService = new PostService();
    private final UserService userService = new UserService();

    public void comment(Long userId, Save dto) {
        if(dto.parentCommentId() == null) {
            noticeService.notice(postService.findWriter(dto.postId()), COMMENT_ON_POST.getMessage(userService.findName(userId)));
        } else {
            Long writer = commentService.findWriter(dto.parentCommentId());
            if(!userId.equals(writer))
                noticeService.notice(writer, COMMENT_ON_COMMENT.getMessage(userService.findName(userId)));
        }

        commentService.save(userId, dto);
        postService.updateCommentCount(dto.postId(), 1L);
    }
}
