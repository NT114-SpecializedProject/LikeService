package com.dat.backend.likeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "likes")
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(name = "blog_id")
    private Long blogId;
    @Column(name = "comment_id")
    private Long commentId;
    private Long createdAt;
}
