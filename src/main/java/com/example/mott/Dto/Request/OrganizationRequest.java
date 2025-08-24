package com.example.mott.Dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrganizationRequest {

    private UUID id;
    private String name;
    private String description;
}