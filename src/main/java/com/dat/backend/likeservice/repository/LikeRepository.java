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
    Long countByCommentId(Long commentId);

    @Query(
            value = "select count(*) from likes where blog_id = ?1",
            nativeQuery = true
    )
    Long countByBlogId(Long blogId);

    Like findByBlogIdAndUserId(Long postId, Long userId);

    Like findByCommentIdAndUserId(Long commentId, Long userId);
}
