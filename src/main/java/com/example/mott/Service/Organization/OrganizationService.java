package com.example.mott.Service.Organization;

import com.example.mott.Dto.Request.OrganizationRequest;
import com.example.mott.Dto.Response.OrganizationResponse;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {

    OrganizationResponse createOrganization(OrganizationRequest organizationRequest);

    OrganizationResponse updateOrganization(OrganizationRequest organizationRequest);

    List<OrganizationResponse> getAllOrganizationsForUser();

    OrganizationResponse getOrganizationById(UUID id);

    void deleteOrganizationById(UUID id);
}