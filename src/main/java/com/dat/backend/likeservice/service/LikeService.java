package com.dat.backend.likeservice.service;

import com.dat.backend.likeservice.dto.LikeRequest;
import com.dat.backend.likeservice.dto.LikeResponse;
import com.dat.backend.likeservice.entity.Like;
import com.dat.backend.likeservice.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final BlogService blogService;

    /**
        * Create a new like
        * @param likeRequest the request object containing userId, postId, and commentId
        * return follow the details of the like which the field is not null
     */
    @Transactional
    public String actionLike(LikeRequest likeRequest) {
        // Get information from the request
        Long blogId = likeRequest.getBlogId();
        // Check the type of like (post or comment)
        if (likeRequest.getBlogId() != null) {
            // if you already like this post or comment, remove the like
            Like existLike = likeRepository.findByBlogIdAndUserId(likeRequest.getBlogId(), likeRequest.getUserId());
            if (existLike != null) {
                // remove the like
                likeRepository.deleteById(existLike.getId());
                log.info("Unlike post from userId: {}, postId: {}", likeRequest.getUserId(), likeRequest.getBlogId());
                blogService.decreaseLike(blogId);
                return "unlike";
            }
        }
        else {
            Like existLike = likeRepository.findByCommentIdAndUserId(likeRequest.getCommentId(), likeRequest.getUserId());
            if (existLike != null) {
                likeRepository.deleteById(existLike.getId());
                return "dislike comment";
            }
        }


        log.info("Create like from userId: {}, postId: {}, commentId: {}", likeRequest.getUserId(), likeRequest.getBlogId(), likeRequest.getCommentId());
        // Create a new like if the like doesn't exist
        Like like = Like.builder()
                .userId(likeRequest.getUserId())
                .blogId(likeRequest.getBlogId())
                .commentId(likeRequest.getCommentId())
                .createdAt(System.currentTimeMillis())
                .build();
        likeRepository.save(like);
        blogService.increaseLike(blogId);
        log.info("Like created with ID: {}", like.getId());
        // Map the like to LikeResponse
        // If commentId is null, it means it's a post like
        return likeRequest.getCommentId() == null ?
                "like" :
                "like comment";
    }
}
