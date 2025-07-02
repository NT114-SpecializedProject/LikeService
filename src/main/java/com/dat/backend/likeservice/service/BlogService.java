package com.dat.backend.likeservice.service;

import com.dat.backend.likeservice.dto.BlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "blog-service",
        url = "${service.blog.url}"
)
public interface BlogService {
    @GetMapping("/api/v1/blog/{id}")
    BlogResponse getBlogById(@PathVariable Long id);

    @PostMapping("/api/v1/blog/like/{id}/increase")
    String increaseLike(@PathVariable Long id);

    @PostMapping("/api/v1/blog/like/{id}/decrease")
    String decreaseLike(@PathVariable Long id);
}
