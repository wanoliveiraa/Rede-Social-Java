package com.example.sysmap.parrot.Damon.Entities;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {
    
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String imageUrl;
    private Boolean photo= false;
    private String username;
    private ArrayList<UUID> followers = new ArrayList<>();;
    private ArrayList<UUID> following = new ArrayList<>();;

    public User(String name, String email,String username) {
        this.id=UUID.randomUUID();
        this.email=email;
        this.username=username;
    }
    
    public void followUser(String userId) {
        if (!this.following.contains(UUID.fromString(userId))) {
            this.following.add(UUID.fromString(userId));
        }
        if (followers.contains(this.id)) {
           followers.add(this.id);
        }
    }

    public void unfollowUser(String userId) {
        if (this.following.contains(UUID.fromString(userId))) {
            this.following.remove(UUID.fromString(userId));
        }
        if (followers.contains(this.id)) {
            followers.remove(this.id);
        }
    }

}
