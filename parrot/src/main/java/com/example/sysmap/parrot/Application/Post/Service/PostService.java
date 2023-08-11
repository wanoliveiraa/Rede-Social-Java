package com.example.sysmap.parrot.Application.Post.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sysmap.parrot.Application.Comment.CommentReponse;
import com.example.sysmap.parrot.Application.Comment.CommentRequest;
import com.example.sysmap.parrot.Application.Exception.DatabaseException;
import com.example.sysmap.parrot.Application.Exception.Exceptions;
import com.example.sysmap.parrot.Application.Post.Dto.PostRequestReponse.PostReponse;
import com.example.sysmap.parrot.Application.Post.Dto.PostRequestReponse.PostRequest;
import com.example.sysmap.parrot.Application.User.Services.IUserService;
import com.example.sysmap.parrot.Config.Aws.IAwsFileService;
import com.example.sysmap.parrot.Damon.Entities.Comment;
import com.example.sysmap.parrot.Damon.Entities.Post;
import com.example.sysmap.parrot.Damon.Entities.User;
import com.example.sysmap.parrot.Infrastructure.IPostRepository;



@Service
public class PostService implements IPostService {
    
    
    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IAwsFileService awsFileService;


    private User authenticateUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String createPost(String content,MultipartFile photo){
        
        var userAuth = authenticateUser(); 
        var post = new Post(userAuth.getId(),content); 

        String photoUri = null;
        
        if(content == null && photo == null ){
            throw new Exceptions("O post deve ter pelo menos um texto ou imagem");
        }
        
        if(photo != null) {
            var fileName = post.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
            try {
                photoUri = awsFileService.upload(photo, fileName);
            } catch (Exception e) {
                throw new Exceptions("Erro ao fazer upload da foto");
            }

            post.setImage(photoUri);
        }
       
        try {
            
            
            postRepository.save(post);

            return post.getId().toString();
            
        } catch (Exception e) {
            throw new DatabaseException("Erro em salvar o post", e);
        }
 
            
    }

    
    @Override
    public List<PostReponse> findAllPost() {
        var posts = postRepository.findAll();
        List<PostReponse> reponses= new ArrayList<>();
        for(Post post : posts) {
            var postResponse = new PostReponse(post);
            reponses.add(postResponse);
        }
        return reponses;
    }

    @Override
    public List<PostReponse> findPostById(String id) {
    
        List<Post> posts = postRepository.findPostById(UUID.fromString(id));
        List<PostReponse> reponses= new ArrayList<>();
        for(Post post : posts) {
            var postResponse = new PostReponse(post);
            reponses.add(postResponse);
        }
    
        return reponses;

    }

    @Override
    public Post getPostById (String id){
        return postRepository.findById(UUID.fromString(id)).orElse(null);
        
    }

    @Override
    public String deletePostbyId(String id) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(id)).orElse(null);

            if (post == null) {
                throw new Exceptions("Post não encontrado");
            }

            if (!userAuth.getId().equals(post.getUserId())) {
                throw new Exceptions("Você não tem permissão para excluir este post");
            }

            postRepository.delete(post);

            return "Post excluído com sucesso";
    }

    @Override
    public String udpatePost(CommentRequest request, String postId) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);

        post.setContent(request.content);
        postRepository.save(post);

        return post.getId().toString();
    }
    
    @Override
    public String createCommentPost(String postId, CommentRequest request) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);

        if (post == null) {
            throw new Exceptions("Post não encontrado");
        }

        var comment = new Comment(request.content, userAuth.getId());

        post.getComments().add(comment);
        postRepository.save(post);
        return comment.getId().toString();
    }


    @Override
    public String LikePost(String postId) {
        var userAuth = authenticateUser();
    
      
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);
    
        if (post == null) {
            throw new Exceptions("Post não encontrado!");
        }
    
        // Verifica se o usuário já deu like na postagem
        boolean userLikedPost = post.getLikes().contains(userAuth.getId());
    
        // Adiciona ou remove o like dependendo da situação atual
        if (userLikedPost) {
            post.getLikes().remove(userAuth.getId());
            postRepository.save(post);
            return "Like removido";
        } else {
            post.getLikes().add(userAuth.getId());
            postRepository.save(post);
            return "Like adicionado";
        }
       
    }
    @Override
    public String likeComment(String postId, String commentId) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);
    
        if (post == null) {
            throw new Exceptions("Post não encontrado!");
        }
    

        Comment comment = null;
        for (Comment c : post.getComments()) {
            if (c.getId().equals(UUID.fromString(commentId))) {
                comment = c;
                break;
            }
        }
    
        if (comment == null) {
            throw new Exceptions("Comentário não encontrado!");
        }
    
       
        boolean userLikedComment = comment.getLikes().contains(userAuth.getId());
    
        if (userLikedComment) {
            comment.getLikes().remove(userAuth.getId());
            postRepository.save(post);
            return "Like removido";
        } else {
            comment.getLikes().add(userAuth.getId());
            postRepository.save(post);
            return "Like adicionado";
        }
    }


    @Override
    public List<CommentReponse> findAllCommentById(String id) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(id)).orElse(null);
        
        if (post == null) {
            throw new Exceptions("Post não encontrado!");
        }
        
        var responses = new ArrayList<CommentReponse>();
        for (Comment comment : post.getComments()) {
            responses.add(new CommentReponse(comment));
        }
        
        return responses;
    }

    @Override
    public String deleteCommetPost(String postId) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);
    
        if (post == null) {
            return "Post nao encontrado";
        }
    
        for (Comment comment : post.getComments()) {
            if (comment.getUserId().equals(userAuth)) {
                post.getComments().remove(comment);
                postRepository.save(post);
                return "Comentario deletado com successo";
            }
        }
        return "Commentario nao encotrado ou user sem permissão";
       
    }


    @Override
    public String udpateComment(CommentRequest request, String postId) {
        var userAuth = authenticateUser();
        var post = postRepository.findById(UUID.fromString(postId)).orElse(null);
        
        if (post == null) {
            return "Post nao encotrado";
        }

        for (Comment comment : post.getComments()) {
            if (comment.getId().equals((request.id)) && comment.getUserId().equals(userAuth)) {
                comment.setContext(request.content);
                postRepository.save(post);
                return request.content;
            }
        }

        return "Commentario nao encotrado ou user sem permissão";

    }
}

   
