package com.postservice.Controler;

import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostModel>> allUser(){
        return new ResponseEntity<>(postService.allUser(), HttpStatus.ACCEPTED);
    }
}
