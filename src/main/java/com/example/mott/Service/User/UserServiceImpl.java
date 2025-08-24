package com.example.mott.Service.User;

import com.example.mott.Dto.Request.UserRequest;
import com.example.mott.Dto.Response.UserResponse;
import com.example.mott.Exception.User.UserNotFoundException;
import com.example.mott.Mapper.User.UserMapper;
import com.example.mott.Model.User;
import com.example.mott.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.mapToUser(userRequest);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + userRequest.getId()));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to user");
        }
        userMapper.mapToUserEntity(userRequest, user);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.mapToUserResponse(users);
    }

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to user");
        }
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to user");
        }
        userRepository.delete(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with Email: " + email));
        return userMapper.mapToUserResponse(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), "",
                true, true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Override
    public UserResponse fetchOrCreateUserFromOAuth(String email, String displayName) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setDisplayName(displayName);
                    return userRepository.save(newUser);
                });
        return userMapper.mapToUserResponse(user);
    }
}