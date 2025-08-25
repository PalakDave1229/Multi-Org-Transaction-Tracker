package com.example.mott.Config;

import com.example.mott.Service.User.UserService;
import com.example.mott.Utility.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Check for existing JWT in header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // If no valid JWT, attempt to generate one using security context or OAuth2 client
        if (username == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) authentication.getPrincipal()).getUsername();
                jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
                response.setHeader("Authorization", "Bearer " + jwt);
                request.setAttribute("Authorization", "Bearer " + jwt);
            } else if (authentication != null && authentication.getName() != null && !authentication.getName().isEmpty()) {
                OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", authentication.getName());
                if (authorizedClient != null) {
                    username = authorizedClient.getPrincipalName();
                    if (username != null) {
                        UserDetails userDetails = userService.loadUserByUsername(username);
                        jwt = jwtUtil.generateToken(userDetails);
                        response.setHeader("Authorization", "Bearer " + jwt);
                        request.setAttribute("Authorization", "Bearer " + jwt);
                    }
                }
            }
        }

        // Authenticate if username is found and no authentication exists
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}