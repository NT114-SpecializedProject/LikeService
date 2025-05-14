package com.dat.backend.likeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequest {
    private Long userId;
    private Long postId;
    private Long commentId;
}
