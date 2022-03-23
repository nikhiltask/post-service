package com.postservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PostDto {
    @Id
    private String postID;
    @NotEmpty(message = "post is required")
    private String post;
    @NotEmpty(message = "postedBy ID is required")
    private String postedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCounts;
    private int likeCounts;
}
