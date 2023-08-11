package com.example.sysmap.parrot.Damon.Entities;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;



@Data
public class Comment {

    @Id
    private UUID id;
    private String context;
    private UUID userId;
    private ArrayList<UUID> likes;
    private ArrayList<Comment> comments;

    
    public Comment(String context, UUID userId) {
        this.id = UUID.randomUUID();
        this.context = context;
        this.userId = userId;
        this.likes = new ArrayList<>();
        this.comments= new ArrayList<>();
    }
    
    
}
