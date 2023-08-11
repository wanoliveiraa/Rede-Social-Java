package com.example.sysmap.parrot.Application.Authenticate.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sysmap.parrot.Application.Authenticate.Dto.AuthenticateRequestReponse.AuthenticateReponse;
import com.example.sysmap.parrot.Application.Authenticate.Dto.AuthenticateRequestReponse.AuthenticateRequest;
import com.example.sysmap.parrot.Application.User.Services.IUserService;
import com.example.sysmap.parrot.Config.Security.IJwtService;

@Service
public class AuthenticantonService implements IAuthenticationService {
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Override
    public AuthenticateReponse authenticate(AuthenticateRequest request) throws Exception{
        var user = userService.getUserEmail(request.email);

        if(user == null){
            return null;
        }

        if(!passwordEncoder.matches(request.password,user.getPassword())){
           throw new Exception("Credencias invalidas!");
        }

        var token  = jwtService.generateToken(user.getId());

        var reponse = new AuthenticateReponse();
        reponse.setUserID(user.getId());
        reponse.setToken(token);

        return reponse;
    }
}
