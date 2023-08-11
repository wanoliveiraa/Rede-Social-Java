package com.example.sysmap.parrot.Application.User.Dto.UserRequestResponse;



import lombok.Data;

@Data
public class UserRequest { 
    public String name;
    public String email;
    public String password;
    public String imageUrl;
    public Boolean photo= false;
    public String username;
    
}
