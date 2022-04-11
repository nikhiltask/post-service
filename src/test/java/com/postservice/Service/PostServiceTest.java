package com.postservice.Service;

import com.postservice.Enum.BloodGroup;
import com.postservice.Enum.Gender;
import com.postservice.Exception.PostNotFoundException;
import com.postservice.Feign.CommentService;
import com.postservice.Feign.LikeService;
import com.postservice.Feign.UserService;
import com.postservice.Model.PostDto;
import com.postservice.Model.PostModel;
import com.postservice.Model.User;
import com.postservice.Repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserService userService;

    @Mock
    CommentService commentService;

    @Mock
    LikeService likeService;


    private PostModel createPostModel() {
        return new PostModel("1", "My Post", "123", null, null);
    }

    private PostDto createPostDTO() throws ParseException {
        return new PostDto("1", "My Post", createUser(), 1, 1, null, null);
    }

    private User createUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2022-03-20");
        user.setUserID("123");
        user.setFirstName("Nikhil");
        user.setMiddleName("a");
        user.setLastName("Mike");
        user.setPhoneNumber("7894561230");
        user.setEmail("nik@Gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("741");
        user.setBloodGroup(BloodGroup.A_POS);
        user.setGender(Gender.MALE);
        return user;

    }

    @Test
    void findById() throws ParseException {
        PostModel postModel = createPostModel();
        PostDto commentDTO = createPostDTO();

        when(this.postRepository.findById("1")).thenReturn(Optional.of(postModel));
        assertThat(this.postService.findById("1").getPost()).isEqualTo(commentDTO.getPost());
        assertThrows(PostNotFoundException.class, () -> this.postService.findById("2"));

    }

    @Test
    void update() throws ParseException {
        PostModel postModel = createPostModel();
        PostDto postDto = createPostDTO();
        when(this.postRepository.findById("1")).thenReturn(Optional.of(postModel));
        when(this.postRepository.save(postModel)).thenReturn(postModel);
        when(this.postRepository.findById("1")).thenReturn(Optional.of(postModel));

        assertThat(this.postService.update(postModel, "1").getPost()).isEqualTo(
                postDto.getPost()
        );
        assertThrows(PostNotFoundException.class, () -> this.postService.update(postModel,
                "12"));
    }

    @Test
    void deleteId() throws ParseException {
        PostDto postDto = createPostDTO();
        PostModel postModel = createPostModel();
        when(this.postRepository.findById("1")).thenReturn(Optional.of(postModel));
        postService.deleteId("1");
        verify(postRepository, times(1)).deleteById("1");
    }

    @Test
    void allUser()   {
        PostModel postModel = createPostModel();
        List<PostModel> list = new ArrayList<>();
        list.add(postModel);

        PageImpl<PostModel> pageImpl = new PageImpl<PostModel>(list);
        Pageable firstPage = PageRequest.of(1, 2);
        when(this.postRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.postService.allUser(1, 2).size());
        verify(this.postRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void userPost() throws ParseException {
        PostDto postDto = createPostDTO();
        PostModel postModel = createPostModel();
        when(this.postRepository.findById("1")).thenReturn(Optional.of(postModel));
        Mockito.when(this.postRepository.save(any(PostModel.class))).thenReturn(postModel);
        assertThat(this.postService.userPost(postModel).getPost()).isEqualTo(postDto.getPost());


    }
}