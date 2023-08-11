package com.example.sysmap.parrot.Application.User.Services;

import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sysmap.parrot.Application.Exception.DatabaseException;
import com.example.sysmap.parrot.Application.Exception.Exceptions;
import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserRequest;
import com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse.UserResponse;
import com.example.sysmap.parrot.Config.Aws.IAwsFileService;
import com.example.sysmap.parrot.Damon.Entities.User;
import com.example.sysmap.parrot.Infrastructure.IUserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAwsFileService awsFileService;
    
    @Override
    public String createUser(UserRequest request)  {
       
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new Exceptions("User com email " + request.email + " ja existe");
        }
        
        try {
            var hash = passwordEncoder.encode(request.password);
            
           
            var user = new User(request.name, request.email, request.username);
            user.setPassword(hash);
           

            userRepository.save(user);
        
            return user.getId().toString();
        } catch (Exception e) {
            throw new DatabaseException("Erro em salvar o usuário", e);
        }
    }
    @Override
    public UserResponse findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        var response = user.map(u -> new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getPassword(),
                u.getImageUrl(),
                u.getUsername()
            )).orElse(null);
            
        return response;
    }

    @Override
    public String deleteById(String id) throws DatabaseException{
       var idAux =UUID.fromString(id);
       try{
            userRepository.deleteById(idAux);
           return "Usuario Deletado" ;
       }catch (Exception e){
            throw new DatabaseException("Erro em deletar", e);
       }
        
    }
    
    @Override
    public List<UserResponse> findAll() {
        var users = userRepository.findAll();
        var responses = new ArrayList<UserResponse>();
        for (var user : users) {
            var response = new UserResponse(user);
            responses.add(response);
        }
        return responses;
    }
   
    @Override
    public UserResponse updateUser(String id ,UserRequest request){
        UUID idAux = UUID.fromString(id);
        User user = userRepository.findById(idAux).get();
        var hash = passwordEncoder.encode(request.password);

        user.setName(request.name);
        user.setEmail(request.email);
        user.setPassword(hash);
        user.setUsername(request.username);
        user.setImageUrl(request.imageUrl);
        user.setUsername(request.username);

        userRepository.save(user);
        
        var response = new UserResponse(user.getId(), 
        user.getName(), 
        user.getEmail(),
        user.getPassword(),
        user.getImageUrl(),
        user.getUsername()
        );

        return response;

    }
    @Override
    public User getUserEmail(String email) {
        var user = userRepository.getByEmail(email);
        return user;
 
    }
    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
        
    }
    @Override
    public void uploadPhoto(MultipartFile photo) throws Exceptions {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        var photoUri = "";

        try {
            var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
            photoUri = awsFileService.upload(photo, fileName);
        } catch (Exceptions e) {
            throw new Exceptions(e.getMessage());
        }

        user.setImageUrl(photoUri);
        userRepository.save(user);
    }

    @Override
    public String follow(String id) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        
        if (user.getId().toString().equals(id)) {
            throw new Exceptions("Não é possível seguir seu próprio perfil");
        }else{
            user.followUser(id);
            userRepository.save(user);
            return "Seguindo o amigo ";
        }   
        

    }
    @Override
    public String unfllow(String id) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        
        if (user.getId().toString().equals(id)) {
            throw new Exceptions("Não é possível seguir seu próprio perfil");
        }else{
            user.unfollowUser(id);
            userRepository.save(user);
            return "Deixando ser amigo ";
        }   
    }
    @Override
    public List<UserResponse> listFollowersByUsername(String username) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user = userRepository.getByUsername(username);
        if (user == null) {
            throw new Exceptions("Usuário não encontrado");
        }
        var responses = new ArrayList<UserResponse>();
        for (UUID followerId : user.getFollowers()) {
            var follower = userRepository.findById(followerId).orElse(null);
            if (follower != null) {
                var response = new UserResponse(follower);
                responses.add(response);
            }
        }
        return responses;


    }
    @Override
    public List<UserResponse> listFollowingByusername(String username) {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user = userRepository.getByUsername(username);
        var responses = new ArrayList<UserResponse>();
        var following = user.getFollowing();
        for (UUID followingId : following) {
            var followingUser = userRepository.findById(followingId).orElse(null);
            if (followingUser != null) {
                responses.add(new UserResponse(followingUser));
            }
        }
        return responses;
    }
  
    


    @Override
    public User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }

}

