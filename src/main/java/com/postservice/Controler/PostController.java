package com.postservice.Controler;

import com.postservice.Model.PostDto;
import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostModel> findById(@PathVariable("postId") String postId){
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostModel> update(@Valid @RequestBody PostModel postModel, @PathVariable("postId") String postId){
        return  new ResponseEntity<>(postService.update(postModel,postId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deleteId(@PathVariable("postId") String postId){
        return  new ResponseEntity<>(postService.deleteId(postId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> allUser(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize){
        return new ResponseEntity<>(postService.allUser(page,pageSize), HttpStatus.ACCEPTED);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostModel> userPost(@RequestBody @Valid PostModel postModel){
        return  new ResponseEntity<>(postService.userPost(postModel), HttpStatus.ACCEPTED);
    }
}
