package com.example.sysmap.parrot.Application.User.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserRequest;
import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserResponse;
import com.example.sysmap.parrot.Damon.Entities.User;

public  interface IUserService {
    public String createUser(UserRequest request); 
    public UserResponse findByEmail(String email);
    public String deleteById(String id);
    public List<UserResponse> findAll();
    public UserResponse updateUser(String id ,UserRequest request);
    public User getUserEmail(String email);
    public User getUserById(UUID id);
    public User getUserByUsername(String username);
    public void uploadPhoto(MultipartFile photo);
    public String follow(String id);
    public String unfllow(String id);
    public List<UserResponse> listFollowersByUsername(String username);
    public List<UserResponse> listFollowingByusername(String username);
    
}
