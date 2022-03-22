package com.postservice.Service;

import com.postservice.Model.PostModel;
import com.postservice.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    public List<PostModel> allUser(){
        return postRepository.findAll();
    }
}
