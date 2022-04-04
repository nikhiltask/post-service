package com.postservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="comment-service")
public interface CommentService {
    @GetMapping("/posts/{postId}/comments/count")
    public int commentCount(@PathVariable("postId") String postId);
}
