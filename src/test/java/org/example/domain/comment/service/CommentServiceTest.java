package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO;
import org.example.domain.comment.dto.CommentReqDTO.Update;
import org.example.domain.comment.dto.CommentResDTO;
import org.example.domain.comment.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 댓글_작성() {
        // given
        Long userId = 1L;
        CommentReqDTO.Save parentCommentDTO = new CommentReqDTO.Save("Parent comment", 1L, null);

        // when
        commentService.save(userId, parentCommentDTO);
    }

    @Test
    void 대댓글_작성() {
        // given
        Long userId = 1L;
        CommentReqDTO.Save childCommentDTO = new CommentReqDTO.Save("Child comment", 1L, 4L);

        // when
        commentService.save(userId, childCommentDTO);
    }

    @Test
    void 댓글_조회() {
        // given
        Long postId = 1L;
        CommentResDTO.Detail parentComment = new CommentResDTO.Detail(
                1L, "Parent comment", 10L, "1시간 전", "profileImg1", "User1", true, List.of()
        );
        CommentResDTO.Detail childComment = new CommentResDTO.Detail(
                2L, "Child comment", 5L, "30분 전", "profileImg2", "User2", false, List.of()
        );

        parentComment = parentComment.withChildComments(List.of(childComment));

        when(commentRepository.findComments(postId)).thenReturn(List.of(parentComment));

        // when
        List<CommentResDTO.Detail> comments = commentService.read(postId);

        comments.listIterator().forEachRemaining(System.out::println);
    }

    @Test
    void 댓글_삭제() {
        Long commentId = 4L;
        Long postId = 1L;
        commentService.delete(commentId, postId);
    }

    @Test
    void 댓글_수정() {
        Long commentId = 7L;
        commentService.update(commentId, new Update("Updated comment content"));
    }
}
