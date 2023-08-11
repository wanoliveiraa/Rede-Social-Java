package com.example.sysmap.parrot.Application.Comment;

import java.util.ArrayList;
import java.util.UUID;

import com.example.sysmap.parrot.Damon.Entities.Comment;

import lombok.Data;

@Data
public class CommentReponse  {
    public UUID id;
    public String context;
    public UUID userId;
    public ArrayList<UUID> likes;
    public ArrayList<Comment> comments;
    public CommentReponse(Comment comment) {
        this.id = comment.getId();
        this.context =comment.getContext();
        this.userId = comment.getUserId();
        this.likes = comment.getLikes();
        this.comments = comment.getComments();
    }

}
