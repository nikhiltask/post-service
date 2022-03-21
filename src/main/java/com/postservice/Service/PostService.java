package com.postservice.Service;

import com.postservice.Model.PostModel;
import com.postservice.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostModel update(PostModel postModel, String postId){
        postModel.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postModel);
    }
}
