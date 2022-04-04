package com.postservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="like-service")
public interface LikeService {

    @GetMapping("/postsOrComments/{postOrCommentId}/likes/count")
    public int likeCount( @PathVariable("postOrCommentId") String postOrCommentId);
}
