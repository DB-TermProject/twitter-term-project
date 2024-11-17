package org.example.domain.comment.usecase;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.dto.CommentReqDTO.Update;
import org.example.domain.comment.dto.CommentResDTO.Detail;
import org.example.domain.comment.service.CommentService;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.post.service.PostService;
import org.example.domain.user.service.UserService;
import org.example.util.TransactionManager;

import java.util.List;

import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_COMMENT;
import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_POST;

public class CommentUseCase {

    private final CommentService commentService = new CommentService();
    private final NoticeService noticeService = new NoticeService();
    private final PostService postService = new PostService();
    private final UserService userService = new UserService();
    private final TransactionManager transactionManager = new TransactionManager();

    public void comment(Long userId, Save dto) {
        transactionManager.execute(connection -> {
            if(dto.parentCommentId() == null) {
                noticeService.notice(postService.findWriter(dto.postId()), COMMENT_ON_POST.getMessage(userService.findName(userId)));
            } else {
                Long writer = commentService.findWriter(dto.parentCommentId(), connection);
                if(!userId.equals(writer))
                    noticeService.notice(writer, COMMENT_ON_COMMENT.getMessage(userService.findName(userId)));
            }

            commentService.save(userId, dto, connection);
            postService.updateCommentCount(dto.postId(), 1L);
        });
    }

     public void deleteComment(Long commentId, Long postId) {
        transactionManager.execute(connection -> {
            Long deletedComments = commentService.delete(commentId, connection);
            postService.updateCommentCount(postId, -1 * deletedComments);
        });
     }

     public void updateComment(Long commentId, Update dto) {
        transactionManager.execute(connection -> {
            commentService.update(commentId, dto, connection);
        });
     }

     public List<Detail> readComments(Long postId) {
        return transactionManager.executeReadOnly(connection ->
            commentService.read(postId, connection)
        );
     }
}
