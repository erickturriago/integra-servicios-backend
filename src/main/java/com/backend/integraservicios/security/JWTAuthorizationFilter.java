package com.backend.integraservicios.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            String token = bearerToken.replace("Bearer ", "");
            TokenUtils tokenUtils = new TokenUtils(userDetailsService); // Create an instance

            UsernamePasswordAuthenticationToken usernamePAT = tokenUtils.getAuthentication(token);
            usernamePAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
            System.out.println(usernamePAT);
        }

        filterChain.doFilter(request, response);
    }
}
