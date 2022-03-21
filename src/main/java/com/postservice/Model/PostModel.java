package com.postservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Document(collection = "PostService")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PostModel {

    @Id
    private String postID;

    @NotEmpty(message = "user ID is required")
    private String userID;

    @NotEmpty(message = "Post is required")
    private String post;

    @NotEmpty(message = "Post By is required")
    private String postedBy;


    private Date createdAt;

    private Date updatedAt;

}
