package com.postservice.Service;

import com.postservice.Model.PostModel;
import com.postservice.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostModel update(PostModel postModel, String postId){
      postModel.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postModel);
}
    public String deleteId(String Id){
        postRepository.deleteById(Id);
        return "Post"+Id+ " Deleted Successfully";
    }
    public List<PostModel> allUser(){
        return postRepository.findAll();
    }

    public PostModel userPost(PostModel postModel){
        postModel.setCreatedAt(LocalDateTime.now());
        postModel.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postModel);
    }
}
