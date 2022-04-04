package com.postservice.Service;

import com.postservice.Exception.PostNotFoundException;
import com.postservice.Feign.CommentService;
import com.postservice.Feign.LikeService;
import com.postservice.Feign.UserService;
import com.postservice.Model.PostDto;
import com.postservice.Model.PostModel;
import com.postservice.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userFeign;
    @Autowired
    private LikeService likeFeign;
    @Autowired
    private CommentService commentFeign;


    public PostDto findById(String postId){


            PostModel postModel=postRepository.findById(postId).get();

            PostDto postDTO= new PostDto(postModel.getPostID(),postModel.getPost(),
                    userFeign.findByID(postModel.getPostedBy())
                    ,postModel.getCreatedAt(),postModel.getUpdatedAt(),likeFeign.likeCount(postModel.getPostID()),
                    commentFeign.commentCount(postModel.getPostID()));

            return postDTO;



    }

    public PostModel update(PostModel postModel, String postId) {
        postModel.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postModel);
    }

    public String deleteId(String Id) {
        postRepository.deleteById(Id);
        return "Post" + Id + " Deleted Successfully";
    }

    public List<PostDto> allUser(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable firstPage = PageRequest.of(page - 1, pageSize);
        List<PostModel> postModels = postRepository.findAll(firstPage).toList();
        if (postModels.isEmpty()) {
            throw new PostNotFoundException("Post Does not Exist");
        }
        List<PostDto> postDTOS = new ArrayList<>();
        for (PostModel postModel : postModels) {
            PostDto postDTO = new PostDto(postModel.getPostID(), postModel.getPost(),
                    userFeign.findByID(postModel.getPostedBy()), postModel.getCreatedAt(),
                    postModel.getUpdatedAt(), likeFeign.likeCount(postModel.getPostID()),
                    commentFeign.commentCount(postModel.getPostID()));
            postDTOS.add(postDTO);
        }
        return postDTOS;


    }

    public PostModel userPost(PostModel postModel) {
        postModel.setCreatedAt(LocalDateTime.now());
        postModel.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(postModel);
    }
}
