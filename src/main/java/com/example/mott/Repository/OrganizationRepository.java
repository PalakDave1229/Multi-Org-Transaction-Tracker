package com.example.mott.Repository;

import com.example.mott.Model.Organization;
import com.example.mott.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    List<Organization> findByOwnerUserId(UUID userId);
}
