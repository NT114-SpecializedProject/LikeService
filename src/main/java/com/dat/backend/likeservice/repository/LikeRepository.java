package com.dat.backend.likeservice.repository;

import com.dat.backend.likeservice.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(
            value = "select count(*) from likes where comment_id = ?1",
            nativeQuery = true
    )
    Long countByCommentId(Long postId);

    @Query(
            value = "select count(*) from likes where post_id = ?1",
            nativeQuery = true
    )
    Long countByPostId(Long commentId);

    Long findByPostIdAndUserId(Long postId, Long userId);

    Long findByCommentIdAndUserId(Long commentId, Long userId);
}
