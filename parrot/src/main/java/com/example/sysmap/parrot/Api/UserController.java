package com.example.sysmap.parrot.Api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserRequest;
import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserResponse;
import com.example.sysmap.parrot.Application.User.Services.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @Autowired
    private IUserService userService;


    @PostMapping()
    private ResponseEntity<String> CreateUser(@RequestBody UserRequest request){
        
        var response = userService.createUser(request);

        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping()
    private ResponseEntity<String> Delete(@RequestParam String id){

        var reponse = userService.deleteById(id);

        if(reponse!=null){
            return ResponseEntity.ok(reponse);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    private ResponseEntity<UserResponse>findByemail(@RequestParam String email){
        var reponse = userService.findByEmail(email);
        
        if(reponse!=null){
            return ResponseEntity.ok(reponse);
        }
        return ResponseEntity.badRequest().build();

    }
    
    
    @GetMapping("/all")
    private ResponseEntity<List<UserResponse>> findByAll(){

        var reponse= userService.findAll();
        if(reponse!=null){
            return ResponseEntity.ok(reponse);
        }
        return ResponseEntity.badRequest().build();

    }
   
    @PutMapping()
    private ResponseEntity<UserResponse> updateUsers(@RequestParam String id, @RequestBody UserRequest request){
        
        var reponse = userService.updateUser(id, request);
        
        if(reponse!=null){
            return ResponseEntity.ok(reponse);
        }
        return ResponseEntity.badRequest().build();

    }

    @PostMapping("/friends/follow")
    private ResponseEntity<String> followUser(@RequestParam String id){
        
        var response = userService.follow(id);

        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/friends/unfollow")
    private ResponseEntity<String>  unfollowwUser(@RequestParam String id){
        var response = userService.unfllow(id);

        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/friends/Following")
    private ResponseEntity<List<UserResponse>> Followers(@RequestParam String username){
       
        var response = userService.listFollowersByUsername(username);

        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();

    }
    @GetMapping("/friends/following")
    private ResponseEntity<List<UserResponse>> Following(@RequestParam String username){
       
        var response = userService.listFollowingByusername(username);

        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();

    }
        
    

    
}