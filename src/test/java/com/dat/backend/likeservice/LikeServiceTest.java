package com.dat.backend.likeservice;
import com.dat.backend.likeservice.dto.LikeRequest;
import com.dat.backend.likeservice.dto.LikeResponse;
import com.dat.backend.likeservice.entity.Like;
import com.dat.backend.likeservice.repository.LikeRepository;
import com.dat.backend.likeservice.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class LikeServiceTest {
    private LikeRepository likeRepository;
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeRepository = mock(LikeRepository.class);
        likeService = new LikeService(likeRepository);
    }

    @Test
    void testLikePost_FirstTime_ShouldCreateNewLike() {
        // Given
        LikeRequest request = LikeRequest.builder()
                .userId(1L)
                .postId(100L)
                .build();

        when(likeRepository.findByPostIdAndUserId(100L, 1L)).thenReturn(null);
        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
            Like like = invocation.getArgument(0);
            like.setId(999L); // simulate DB generated ID
            return like;
        });
        when(likeRepository.countByPostId(100L)).thenReturn(1L);

        // When
        LikeResponse response = likeService.createLike(request);

        // Then
        assertNotNull(response);
        assertEquals(999L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(100L, response.getPostId());
        assertEquals(1L, response.getLikeCount());

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void testLikePost_AlreadyLiked_ShouldUnlike() {
        // Given
        LikeRequest request = LikeRequest.builder()
                .userId(1L)
                .postId(100L)
                .build();

        when(likeRepository.findByPostIdAndUserId(100L, 1L)).thenReturn(123L); // existing like ID
        when(likeRepository.countByPostId(100L)).thenReturn(0L);

        // When
        LikeResponse response = likeService.createLike(request);

        // Then
        assertNotNull(response);
        assertEquals(123L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(100L, response.getPostId());
        assertEquals(0L, response.getLikeCount());

        verify(likeRepository).deleteById(123L);
        verify(likeRepository, never()).save(any());
    }

    @Test
    void testLikeComment_FirstTime_ShouldCreateNewLike() {
        // Given
        LikeRequest request = LikeRequest.builder()
                .userId(2L)
                .commentId(300L)
                .build();

        when(likeRepository.findByCommentIdAndUserId(300L, 2L)).thenReturn(null);
        when(likeRepository.save(any(Like.class))).thenAnswer(invocation -> {
            Like like = invocation.getArgument(0);
            like.setId(777L);
            return like;
        });
        when(likeRepository.countByCommentId(300L)).thenReturn(1L);

        // When
        LikeResponse response = likeService.createLike(request);

        // Then
        assertNotNull(response);
        assertEquals(777L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(1L, response.getLikeCount());

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void testLikeComment_AlreadyLiked_ShouldUnlike() {
        // Given
        LikeRequest request = LikeRequest.builder()
                .userId(2L)
                .commentId(300L)
                .build();

        when(likeRepository.findByCommentIdAndUserId(300L, 2L)).thenReturn(888L);
        when(likeRepository.countByCommentId(300L)).thenReturn(0L);

        // When
        LikeResponse response = likeService.createLike(request);

        // Then
        assertNotNull(response);
        assertEquals(888L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(0L, response.getLikeCount());

        verify(likeRepository).deleteById(888L);
        verify(likeRepository, never()).save(any());
    }
}
