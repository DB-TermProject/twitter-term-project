package org.example.domain.comment.service;

import org.example.domain.comment.dto.CommentReqDTO;
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
    void save_parentComment() {
        // given
        Long userId = 12L;
        CommentReqDTO.Save parentCommentDTO = new CommentReqDTO.Save("Parent comment", 2L, null);

        // when
        commentService.save(userId, parentCommentDTO);
    }

    @Test
    void save_childComment() {
        // given
        Long userId = 12L;
        CommentReqDTO.Save childCommentDTO = new CommentReqDTO.Save("Child comment", 2L, 3L);

        // when
        commentService.save(userId, childCommentDTO);
    }

    @Test
    void findComments_withNestedReplies() {
        // given
        Long postId = 2L;
        CommentResDTO.Detail parentComment = new CommentResDTO.Detail(
                1L, "Parent comment", 10L, "1시간 전", "profileImg1", "User1", true, List.of()
        );
        CommentResDTO.Detail childComment = new CommentResDTO.Detail(
                2L, "Child comment", 5L, "30분 전", "profileImg2", "User2", false, List.of()
        );

        parentComment = parentComment.withChildComments(List.of(childComment));

        when(commentRepository.findComments(postId)).thenReturn(List.of(parentComment));

        // when
        List<CommentResDTO.Detail> comments = commentService.findComments(postId);

        comments.listIterator().forEachRemaining(System.out::println);
    }
}
