package com.postservice.Controler;

import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostModel> userPost(@RequestBody @Valid PostModel postModel){
        return  new ResponseEntity<>(postService.userPost(postModel), HttpStatus.ACCEPTED);
    }
}
