package com.example.sysmap.parrot.Damon.Entities;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Post {
    
    @Id
    private UUID id;
    private UUID userId;
    private String content;
    private ArrayList<UUID>likes;
    private ArrayList<Comment> comments;
    private String image;
    
    public Post( UUID userId, String content) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.content = content;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();

    }
   

}
