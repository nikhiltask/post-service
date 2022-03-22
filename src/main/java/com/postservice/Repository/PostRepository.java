package com.postservice.Repository;

import com.postservice.Model.PostModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<PostModel, String> {
}
