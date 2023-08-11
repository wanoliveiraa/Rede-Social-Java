package com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse;

import java.util.List;
import java.util.UUID;


import com.example.sysmap.parrot.Damon.Entities.User;

import lombok.Data;

@Data
public class UserResponse {
    public UUID id;
    public String name;
    public String email;
    public String password;
    public String imageUrl;
    public Boolean photo= false;
    public String username;
   
    
    public UserResponse(UUID id, String name, String email, String password, String imageUrl, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.username = username;
    }


    public UserResponse(User user) {
        this.id=user.getId();
        this.name=user.getName();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.username=user.getUsername();
    }
    
}
