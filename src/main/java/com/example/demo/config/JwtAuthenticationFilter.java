package com.example.demo.config;


import com.example.demo.Service.JWTService;
import com.example.demo.Service.UserService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal( @NotNull HttpServletRequest request,
                                     @NotNull HttpServletResponse response,
                                     @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader= request.getHeader("Authorization");
        final String jwt;
        final String useremail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
//        jwt=authHeader.substring(7);
//        useremail = jwtService.extractUsername(jwt);


        jwt = authHeader.substring(7);

        try {
            useremail = jwtService.extractUsername(jwt);
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT: " + jwt, e);
            filterChain.doFilter(request, response);
            return;
        } catch (Exception e) {
            logger.error("JWT parsing error: " + jwt, e);
            filterChain.doFilter(request, response);
            return;
        }


        if (StringUtils.isNotEmpty(useremail) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(useremail);

            if (jwtService.isTokenValid(jwt , userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
