package com.example.sysmap.parrot.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sysmap.parrot.Application.Comment.CommentRequest;
import com.example.sysmap.parrot.Application.Post.Dto.PostRequestReponse.PostReponse;
import com.example.sysmap.parrot.Application.Post.Dto.PostRequestReponse.PostRequest;
import com.example.sysmap.parrot.Application.Post.Service.IPostService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("api/v1/post")
public class PostController {
    
    @Autowired
    private IPostService postService;

    @PostMapping()
    public ResponseEntity<Object> createPost(@RequestParam String content, @RequestPart MultipartFile photo) {
        var response = postService.createPost(content, photo);

        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();

    }

    @DeleteMapping()
    private ResponseEntity<String> Delete(@RequestParam String id){
        var response = postService.deletePostbyId(id);
        
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
        
    }
    @PutMapping()
    public ResponseEntity<String> updatePost(@RequestParam String postId, @RequestBody CommentRequest request) {
        var response = postService.udpatePost(request, postId);
                
        if(response!=null){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostReponse>> findAllPosts(){
        var response = postService.findAllPost();

        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @GetMapping()
    public ResponseEntity<List<PostReponse>> findPostbyId(@RequestParam  String id){
        var response = postService.findPostById(id);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/comments")
    public ResponseEntity<String> CommentPost(@RequestParam String postId, @RequestBody CommentRequest request) {
        var response = postService.createCommentPost(postId, request);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
        
    }

    @PostMapping("/likes")
    public ResponseEntity<String> likeRemovePost(@RequestParam String postId) {
        var response = postService.LikePost(postId);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
   
    @PostMapping("likes/comments")
    public ResponseEntity<String> likeRemoveComment(@RequestParam  String postId, @RequestParam  String commentId) {
        var response = postService.likeComment(postId, commentId);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/comment")
    public ResponseEntity<String> updateComment(@RequestParam  String postId, @RequestBody CommentRequest request) {
        var response = postService.udpateComment(request, postId);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/comment")
    public ResponseEntity<String> updateComment(@RequestParam  String postId) {
        var response = postService.deleteCommetPost(postId);
        if(response!=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
}
