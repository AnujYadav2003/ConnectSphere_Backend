package com.connect.ConnectSphere.config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

public class jwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt=request.getHeader(com.connect.ConnectSphere.config.jwtConstant.JWT_HEADER);
        System.out.println(jwt);
        if(jwt!=null)
        {
            try{
                String email= com.connect.ConnectSphere.config.JwtProvider.getEmailFromJwtToken(jwt);
                List<GrantedAuthority> authorities=new ArrayList<>();
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch (Exception e){
                throw new BadCredentialsException("invalid token");
            }
        }
//        else {
//            throw new BadCredentialsException("Please provide a token");
//        }
        filterChain.doFilter(request,response);
    }
}

