package com.example.sysmap.parrot.Config;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sysmap.parrot.Application.User.Services.IUserService;
import com.example.sysmap.parrot.Config.Security.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
    
        String servletPath = request.getServletPath();
        if (servletPath.contains("api/v1/authentication") || servletPath.contains("swagger") || servletPath.contains("docs")){
            filterChain.doFilter(request, response);
            return;
        }
        
        
        if (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().endsWith("/api/v1/user")){
            filterChain.doFilter(request, response);
            return;
        }      
         
        
    
        String token = request.getHeader("Authorization");
        String userId = request.getHeader("RequestedBy");
        
        
        if (token == null || userId == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("User nao autenticado");
            return;
        }
        
        boolean isValidToken = jwtService.isValidToken(token.replace("Bearer ", ""), UUID.fromString(userId));
        
        if (!isValidToken){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
           
            response.getWriter().write("Token inválido ou expirado");
            return;
        }
        if (isValidToken){ 
            try {
                var user = userService.getUserById(UUID.fromString(userId));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.getWriter().write(e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }else {
            response.getWriter().write("Token inválido!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
       
        filterChain.doFilter(request, response);
    }
}
    
