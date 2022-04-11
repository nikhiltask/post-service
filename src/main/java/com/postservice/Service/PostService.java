package com.postservice.Service;

import com.postservice.ConstantFiles.ConstantNames;
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


    public PostDto findById(String postId) {
        if (postRepository.findById(postId).isPresent()) {
            return feignStructure(postId);
        } else {
            throw new PostNotFoundException(ConstantNames.ERROR_CODE);
        }
    }

    public PostDto feignStructure(String postId){
        PostModel postModel = postRepository.findById(postId).get();
        PostDto postDto=new PostDto(postModel.getPostID(),postModel.getPost(),
                userFeign.findByID(postModel.getPostedBy()),
                likeFeign.likeCount(postModel.getPostID()),
                commentFeign.commentCount(postModel.getPostID()),
                postModel.getCreatedAt(),postModel.getUpdatedAt());
        return postDto;

    }

    public PostDto update(PostModel postModel, String postId) {
        if (postRepository.findById(postId).isPresent()) {
            postModel.setPostID(postId);
            postModel.setUpdatedAt(LocalDateTime.now());
            postModel.setCreatedAt(postRepository.findById(postId).get().getCreatedAt());
            postRepository.save(postModel);
            PostDto postDto=new PostDto(postModel.getPostID(),postModel.getPost(),
                    userFeign.findByID(postModel.getPostedBy()),
                    likeFeign.likeCount(postModel.getPostID()),
                    commentFeign.commentCount(postModel.getPostID()),
                    postModel.getCreatedAt(),postModel.getUpdatedAt());
            return postDto;
        } else {
            throw new PostNotFoundException(ConstantNames.ERROR_CODE);
        }

    }

    public String deleteId(String Id) {
        if (postRepository.findById(Id).isPresent()) {
            postRepository.deleteById(Id);
            return ConstantNames.SUCCESS_CODE;
        }else {
            throw new PostNotFoundException(ConstantNames.ERROR_CODE);
        }
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
            throw new PostNotFoundException(ConstantNames.SUCCESS_CODE);
        }
        List<PostDto> postDTOS = new ArrayList<>();
        for (PostModel postModel : postModels) {
            PostDto postDto=new PostDto(postModel.getPostID(),postModel.getPost(),
                    userFeign.findByID(postModel.getPostedBy()),
                    likeFeign.likeCount(postModel.getPostID()),
                    commentFeign.commentCount(postModel.getPostID()),
                    postModel.getCreatedAt(),postModel.getUpdatedAt());
            postDTOS.add(postDto);
        }
        return postDTOS;

    }

    public PostDto userPost(PostModel postModel) {
        postModel.setCreatedAt(LocalDateTime.now());
        postModel.setUpdatedAt(postModel.getCreatedAt());
        postRepository.save(postModel);
        PostDto postDto=new PostDto(postModel.getPostID(),postModel.getPost(),
                userFeign.findByID(postModel.getPostedBy()),
                likeFeign.likeCount(postModel.getPostID()),
                commentFeign.commentCount(postModel.getPostID()),
                postModel.getCreatedAt(),postModel.getUpdatedAt());
        return postDto;
    }
}
