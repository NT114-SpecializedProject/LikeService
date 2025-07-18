package com.dat.backend.likeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long blogId;
    private Long commentId;
    private Long createdAt;
    private Long likeCount;
}
