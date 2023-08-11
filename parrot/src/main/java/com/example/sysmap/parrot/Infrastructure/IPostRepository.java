package com.example.sysmap.parrot.Infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sysmap.parrot.Damon.Entities.Post;

public interface IPostRepository extends  MongoRepository<Post, UUID>  {
    List<Post> findPostById(UUID id);
}
