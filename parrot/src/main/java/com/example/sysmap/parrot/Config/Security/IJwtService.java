package com.example.sysmap.parrot.Config.Security;

import java.util.UUID;

public interface IJwtService {
    public String generateToken(UUID userId);
    public boolean isValidToken(String token, UUID userId);
}
