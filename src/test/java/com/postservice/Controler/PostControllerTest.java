package com.postservice.Controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postservice.Enum.BloodGroup;
import com.postservice.Enum.Gender;
import com.postservice.Model.PostDto;
import com.postservice.Model.PostModel;
import com.postservice.Model.User;
import com.postservice.Service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(PostController.class)
class PostControllerTest {
    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() throws Exception {
        PostDto commentDTO =createOnePost();
        Mockito.when(postService.findById("1")).thenReturn(commentDTO);

        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(7)))
                .andExpect(jsonPath("$.postedBy.userID", Matchers.is("1")));
    }

    @Test
    void update() throws Exception {
        PostDto post= createOnePost();
        PostModel postModel =createOnePostModel();
        Mockito.when(postService.update(postModel,"1")).thenReturn(post);
        mockMvc.perform(put("/posts/1")
                        .content(asJsonString(postModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.postedBy.firstName", Matchers.is("Nikhil")));

    }
    @Test
    void deleteId() throws Exception {
        Mockito.when(postService.deleteId("1")).thenReturn("Deleted");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }
    @Test
    void allUser() throws Exception {
        List<PostDto> postDto = createPost();

        Mockito.when(postService.allUser(1, 2)).thenReturn(postDto);

        mockMvc.perform(get("/posts?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[1].postedBy.firstName", Matchers.is("Nikhil")));
    }
    @Test
    public void userPost() throws Exception {
        PostDto post= createOnePost();
        PostModel postModel =createOnePostModel();
        Mockito.when(postService.userPost(postModel)).thenReturn(post);
        mockMvc.perform(post("/posts")
                        .content(asJsonString(postModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.postedBy.firstName", Matchers.is("Nikhil")));
    }

    private PostDto createOnePost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        PostDto postDto = new PostDto();
        postDto.setPostID("1");
        postDto.setPost("Post By PostService");
        postDto.setPostedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        postDto.setLikeCounts(3);
        postDto.setCommentCounts(4);
        return postDto;
    }

    private PostModel createOnePostModel() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        PostModel postModel = new PostModel();
        postModel.setPostID("1");
        postModel.setPost("Post By PostService");
        postModel.setPostedBy("1");
        return postModel;
    }
    private List<PostDto> createPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        List<PostDto> postDto = new ArrayList<>();

        PostDto postDtos = new PostDto();
        postDtos.setPostID("1");
        postDtos.setPost("Post By PostService");
        postDtos.setPostedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        postDtos.setLikeCounts(3);
        postDtos.setCommentCounts(4);

        PostDto postDto2 = new PostDto();
        postDto2.setPostID("2");
        postDto2.setPost("commentTestTwo");
        postDto2.setPostedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        postDto2.setLikeCounts(3);
        postDto2.setCommentCounts(4);
        postDto.add(postDtos);
        postDto.add(postDto2);

        return postDto;
    }
    private PostDto createPostToUpdate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");


        PostDto postDtos = new PostDto();
        postDtos.setPostID("1");
        postDtos.setPost("Post By PostService");
        postDtos.setPostedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        postDtos.setLikeCounts(3);
        postDtos.setCommentCounts(4);
        return postDtos;
    }
}