package com.dat.backend.likeservice.service;

import com.dat.backend.likeservice.dto.LikeRequest;
import com.dat.backend.likeservice.dto.LikeResponse;
import com.dat.backend.likeservice.entity.Like;
import com.dat.backend.likeservice.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;

    /**
        * Create a new like
        * @param likeRequest the request object containing userId, postId, and commentId
        * return follow the details of the like which the field is not null
     */
    public LikeResponse createLike(LikeRequest likeRequest) {
        // Check the type of like (post or comment)
        if (likeRequest.getPostId() != null) {
            // if you already like this post or comment, remove the like
            Long existLikeId = likeRepository.findByPostIdAndUserId(likeRequest.getPostId(), likeRequest.getUserId());
            if (existLikeId != null) {
                // remove the like
                likeRepository.deleteById(existLikeId);
                return LikeResponse.builder()
                        .id(existLikeId)
                        .userId(likeRequest.getUserId())
                        .postId(likeRequest.getPostId())
                        .createdAt(System.currentTimeMillis())
                        .likeCount(likeRepository.countByPostId(likeRequest.getPostId()))
                        .build();
            }
        }
        else {
            Long existLikeId = likeRepository.findByCommentIdAndUserId(likeRequest.getCommentId(), likeRequest.getUserId());
            if (existLikeId != null) {
                likeRepository.deleteById(existLikeId);
                return LikeResponse.builder()
                        .id(existLikeId)
                        .userId(likeRequest.getUserId())
                        .commentId(likeRequest.getCommentId())
                        .createdAt(System.currentTimeMillis())
                        .likeCount(likeRepository.countByCommentId(likeRequest.getCommentId()))
                        .build();
            }
        }


        log.info("Create like from userId: {}, postId: {}, commentId: {}", likeRequest.getUserId(), likeRequest.getPostId(), likeRequest.getCommentId());
        // Create a new like if the like doesn't exist
        Like like = Like.builder()
                .userId(likeRequest.getUserId())
                .postId(likeRequest.getPostId())
                .commentId(likeRequest.getCommentId())
                .createdAt(System.currentTimeMillis())
                .build();
        likeRepository.save(like);
        log.info("Like created with ID: {}", like.getId());
        // Map the like to LikeResponse
        // If commentId is null, it means it's a post like
        return likeRequest.getCommentId() == null ?
                LikeResponse.builder()
                        .id(like.getId())
                        .userId(like.getUserId())
                        .postId(like.getPostId())
                        .createdAt(like.getCreatedAt())
                        .likeCount(likeRepository.countByPostId(like.getPostId()))
                        .build() :
                LikeResponse.builder()
                        .id(like.getId())
                        .userId(like.getUserId())
                        .commentId(like.getCommentId())
                        .createdAt(like.getCreatedAt())
                        .likeCount(likeRepository.countByCommentId(like.getCommentId()))
                        .build();
    }
}
