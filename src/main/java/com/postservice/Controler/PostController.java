package com.postservice.Controler;

import com.postservice.Model.PostDto;
import com.postservice.Model.PostModel;
import com.postservice.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable("postId") String postId){
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostModel> update(@Valid @RequestBody PostModel postModel, @PathVariable("postId") String postId){
        return  new ResponseEntity<>(postService.update(postModel,postId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deleteId(@PathVariable("postId") String postId){
        return  new ResponseEntity<>(postService.deleteId(postId), HttpStatus.ACCEPTED);
    }

    @GetMapping()
    public ResponseEntity<List<PostDto>> allUser(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize){
        return new ResponseEntity<>(postService.allUser(page,pageSize), HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<PostModel> userPost(@RequestBody @Valid PostModel postModel){
        return  new ResponseEntity<>(postService.userPost(postModel), HttpStatus.ACCEPTED);
    }
}
