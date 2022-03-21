package com.postservice.Controler;

import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostModel> findById(@PathVariable("postId") String postId){
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.ACCEPTED);
    }
}
