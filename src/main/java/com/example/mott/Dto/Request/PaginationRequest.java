package com.example.mott.Dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    private Integer pageNumber;
    private Integer pageSize;
}
