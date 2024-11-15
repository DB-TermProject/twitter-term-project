package org.example.domain.post.service;

import org.example.domain.post.dto.PostReqDTO.Save;
import org.example.domain.post.dto.PostResDTO.Detail;
import org.example.domain.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 글쓰기() {
        // given
        Long userId = 3L;
        Save postDto = new Save("This is a test post content");

        // when
        postService.save(userId, postDto);
    }

    @Test
    void 홈_피드_조회() {
        // given
        Long userId = 3L;
        List<Detail> expectedFeed = Arrays.asList(
                new Detail(1L, "Post content 1", 5L, 2L, "5분 전", "Alice", "http://example.com/profile1.jpg", true),
                new Detail(2L, "Post content 2", 10L, 4L, "3시간 전", "Bob", "http://example.com/profile2.jpg", false)
        );

        // when
        when(postRepository.findHomeFeed(userId)).thenReturn(expectedFeed);
        List<Detail> feed = postService.findHomeFeed(userId);

        feed.listIterator().forEachRemaining(System.out::println);
    }
}