package com.example.sysmap.parrot.Application.Authenticate.Services;

import com.example.sysmap.parrot.Application.Authenticate.Dto.AuthenticateRequestReponse.AuthenticateReponse;
import com.example.sysmap.parrot.Application.Authenticate.Dto.AuthenticateRequestReponse.AuthenticateRequest;

public interface IAuthenticationService {
    public AuthenticateReponse authenticate(AuthenticateRequest request) throws Exception;
}
