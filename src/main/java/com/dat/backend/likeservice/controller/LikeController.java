package com.dat.backend.likeservice.controller;

import com.dat.backend.likeservice.dto.LikeRequest;
import com.dat.backend.likeservice.dto.LikeResponse;
import com.dat.backend.likeservice.entity.Like;
import com.dat.backend.likeservice.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;

    // Create a new like
    @PostMapping("/action")
    public ResponseEntity<String> action(@RequestBody LikeRequest likeRequest) {
        return ResponseEntity.ok(likeService.actionLike(likeRequest));
    }
}
