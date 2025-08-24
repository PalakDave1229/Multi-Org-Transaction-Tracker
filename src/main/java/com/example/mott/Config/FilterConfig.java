package com.example.mott.Config;

import com.example.mott.Service.User.UserService;
import com.example.mott.Utility.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

@Configuration
public class FilterConfig {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public FilterConfig(JwtUtil jwtUtil, UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userService, authorizedClientService);
    }
}