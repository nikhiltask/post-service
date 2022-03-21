package com.postservice.Controler;

import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostModel> update(@Valid @RequestBody PostModel postModel, @PathVariable("postId") String postId){
        return  new ResponseEntity<>(postService.update(postModel,postId), HttpStatus.ACCEPTED);
    }

}
