package com.example.mott.Mapper.User;


import com.example.mott.Dto.Request.UserRequest;
import com.example.mott.Dto.Response.UserResponse;
import com.example.mott.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    User mapToUser(UserRequest userRequest);

    void mapToUserEntity(UserRequest userRequest, @MappingTarget User user);

    UserResponse mapToUserResponse(User user);

    List<UserResponse> mapToUserResponse(List<User> userList);
}
