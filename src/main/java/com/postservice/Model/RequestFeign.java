package com.postservice.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestFeign {
    private PostModel postModel;
    private User user;
    private int commentCounts;
    private int likeCounts;
}
