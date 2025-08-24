package com.example.mott.Config;

import com.example.mott.Dto.Response.UserResponse;
import com.example.mott.Service.User.UserService;
import com.example.mott.Utility.JwtUtil;
import com.example.mott.Utility.ResponseStructure;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2SuccessHandler(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String displayName = oAuth2User.getAttribute("name");

        System.out.println("OAuth2 Success: Email = " + email + ", DisplayName = " + displayName);

        UserResponse userResponse = userService.fetchOrCreateUserFromOAuth(email, displayName);
        String jwt = jwtUtil.generateToken(userService.loadUserByUsername(email));

        System.out.println("Generated JWT: " + jwt);

        // Set the JWT in the response header for immediate use
        response.setHeader("Authorization", "Bearer " + jwt);

        ResponseStructure<UserResponse> resp = ResponseStructure.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(userResponse)
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(resp));
    }
}