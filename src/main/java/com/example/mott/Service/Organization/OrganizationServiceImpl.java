package com.example.mott.Service.Organization;

import com.example.mott.Dto.Request.OrganizationRequest;
import com.example.mott.Dto.Response.OrganizationResponse;
import com.example.mott.Exception.Organization.OrganizationNotFoundException;
import com.example.mott.Exception.User.UserNotFoundException;
import com.example.mott.Mapper.Organization.OrganizationMapper;
import com.example.mott.Model.Organization;
import com.example.mott.Model.User;
import com.example.mott.Repository.OrganizationRepository;
import com.example.mott.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Organization organization = organizationMapper.mapToOrganization(organizationRequest);
        organization.setOwner(currentUser);
        organizationRepository.save(organization);
        return organizationMapper.mapToOrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse updateOrganization(OrganizationRequest organizationRequest) {
        Organization organization = organizationRepository.findById(organizationRequest.getId())
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with Id: " + organizationRequest.getId()));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!organization.getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to organization");
        }
        organizationMapper.mapToOrganizationEntity(organizationRequest, organization);
        organizationRepository.save(organization);
        return organizationMapper.mapToOrganizationResponse(organization);
    }

    @Override
    public List<OrganizationResponse> getAllOrganizationsForUser() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Organization> organizations = organizationRepository.findByOwnerUserId(user.getUserId());
        return organizationMapper.mapToOrganizationResponse(organizations);
    }

    @Override
    public OrganizationResponse getOrganizationById(UUID id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!organization.getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to organization");
        }
        return organizationMapper.mapToOrganizationResponse(organization);
    }

    @Override
    public void deleteOrganizationById(UUID id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!organization.getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to organization");
        }
        organizationRepository.delete(organization);
    }
}