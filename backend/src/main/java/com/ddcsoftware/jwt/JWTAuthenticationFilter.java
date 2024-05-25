package com.ddcsoftware.jwt;

import com.ddcsoftware.customer.CustomerUserDetailsService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Create a class that intercepts each request
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    //injections
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;//importing srping's interface

    public JWTAuthenticationFilter(JWTUtil jwtUtil,
                                   UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    //this functions has access to each request, response and filter chain
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        //Get the content of the Header "Authorization" which is where we sent the JWT Token
        String authHeader = request.getHeader("Authorization");

        /*
        If authHeader is null or Bearer.
        Does not contain Bearer: in this case this token
        does not need proof of possession, meaning
        that anyone with access to the token can use it.
        If token gets leaked then the owner can get impersonated.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            //this rejects the request
            filterChain.doFilter(request, response);
            return;
        }

        //get jwt claims what are after "Bearer" (which is 7 chars long)
        String jwt = authHeader.substring(7);

        //getting subject from jwt body
        String subject = jwtUtil.getSubject(jwt);

        /*
        If subject has content && SecurityContextHolder is null proceed.
        SecurityContextHolder is Spring's class that contains info
        about principal who is authenticated (current user who is doing request).
        If is null means that is not yet authenticated, and we proceed to authenticate.
         */
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //get the user details from the subject (email)
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            //Check if current token is valid
            if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())){
                //Create an authentication token from Spring's class UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                //set the authentication details from the request
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //set the SecurityContextHolder with the authentication token
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //this will move on to the next filter in the chain (as in continue)
        filterChain.doFilter(request, response);
    }
}
