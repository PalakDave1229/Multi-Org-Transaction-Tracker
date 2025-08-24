package com.example.mott.Service.User;

import com.example.mott.Dto.Request.UserRequest;
import com.example.mott.Dto.Response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID id);

    void deleteUserById(UUID id);

    UserResponse getUserByEmail(String email);

    UserResponse fetchOrCreateUserFromOAuth(String email, String displayName);
}