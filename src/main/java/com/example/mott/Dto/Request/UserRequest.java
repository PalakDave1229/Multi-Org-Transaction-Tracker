package com.example.mott.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRequest {

    private UUID id;
    private String email;
    private String displayName;
}
