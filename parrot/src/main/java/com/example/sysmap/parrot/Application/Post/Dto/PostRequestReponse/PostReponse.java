package com.example.sysmap.parrot.Application.Post.Dto.PostRequestReponse;

import java.util.ArrayList;
import java.util.UUID;

import com.example.sysmap.parrot.Damon.Entities.Comment;
import com.example.sysmap.parrot.Damon.Entities.Post;
import com.example.sysmap.parrot.Damon.Entities.User;

import lombok.Data;


@Data
public class PostReponse {
    private UUID id;
    private String content;
    private UUID userId;
    private ArrayList<UUID>likes;
    private ArrayList<Comment> comments;
    private String image;
    
    public PostReponse(UUID id, String content, UUID userId, ArrayList<UUID> likes, ArrayList<Comment> comments,
            String image) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.likes = likes;
        this.comments = comments;
        this.image = image;
    }
    public PostReponse(Post post) {
        this.id = post.getId();
        this.content =post.getContent();
        this.userId = post.getUserId();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.image = post.getImage();
    }
   
   
   

}
