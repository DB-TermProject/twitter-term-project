package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO.Save;
import org.example.domain.comment.dto.CommentReqDTO.Update;
import org.example.domain.comment.repository.CommentRepository;
import org.example.domain.notice.service.NoticeService;
import org.example.domain.post.service.PostService;
import org.example.domain.user.service.UserService;

import java.util.List;

import static org.example.domain.comment.dto.CommentResDTO.Detail;
import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_COMMENT;
import static org.example.domain.notice.enums.NoticeMessage.COMMENT_ON_POST;

public class CommentService {

    private final CommentRepository commentRepository = new CommentRepository();
    private final PostService postService = new PostService();
    private final NoticeService noticeService = new NoticeService();
    private final UserService userService = new UserService();

    public void save(Long userId, Save dto) {
        commentRepository.save(userId, dto);
    }

    public Long findWriter(Long commentId) {
        return commentRepository.findWriter(commentId);
    }

    public List<Detail> read(Long postId) {
        return commentRepository.findComments(postId);
    }

    public void update(Long commentId, Update dto) {
        commentRepository.updateComment(commentId, dto);
    }

    public void delete(Long commentId, Long postId) {
        Long deletedComments = commentRepository.delete(commentId);
        postService.updateCommentCount(postId, -1 * deletedComments);
    }

    public void updateLikeCount(Long commentId, Long value) {
        commentRepository.updateLikeCount(commentId, value);
    }
}
