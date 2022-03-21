package com.postservice.Service;

import com.postservice.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    public String deleteId(String Id){
        postRepository.deleteById(Id);
        return "Post"+Id+ " Deleted Successfully";
    }
}
