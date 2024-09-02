package com.example.AccountStatement.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring security filter chain");

        http
            .authorizeHttpRequests(authorizeRequests -> {
                logger.debug("Configuring authorize requests");
                authorizeRequests
                    .requestMatchers("/", "/login", "/api/login", "/error").permitAll()
                    .anyRequest().authenticated();
            })
            .csrf(csrf -> {
                logger.debug("Disabling CSRF protection");
                csrf.disable();
            }) // Disable CSRF for simplicity
            .sessionManagement(session -> {
                logger.debug("Configuring session management");
                session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
            })
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
