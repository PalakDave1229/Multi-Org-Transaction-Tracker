package com.example.mott.Mapper.Organization;

import com.example.mott.Dto.Request.OrganizationRequest;
import com.example.mott.Dto.Response.OrganizationResponse;
import com.example.mott.Model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface OrganizationMapper {

    Organization mapToOrganization(OrganizationRequest organizationRequest);

    void mapToOrganizationEntity(OrganizationRequest organizationRequest, @MappingTarget Organization organization);

    OrganizationResponse mapToOrganizationResponse(Organization organization);

    List<OrganizationResponse> mapToOrganizationResponse(List<Organization> organizationList);
}