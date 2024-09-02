package com.example.AccountStatement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.AccountStatement.model.dto.LoginRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public boolean login(LoginRequest loginRequest, HttpServletRequest request) throws AuthenticationException {
        logger.info("Authenticating user: {}", loginRequest.getUsername());

        // Load the user details from the UserDetailsServiceImpl
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        // Create an authentication token with the username, password, and authorities
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(userDetails, loginRequest.getPassword(), userDetails.getAuthorities());

        try {
            // Authenticate the token using the authentication manager
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                logger.info("Authentication successful for user: {}", loginRequest.getUsername());
                return true;
            } else {
                logger.error("Authentication failed for user: {}", loginRequest.getUsername());
                return false;
            }
        } catch (AuthenticationException e) {
            logger.error("AuthenticationException for user: {}", loginRequest.getUsername(), e);
            throw e;
        }
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }
}

