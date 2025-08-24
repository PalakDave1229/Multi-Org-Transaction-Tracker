package com.example.mott.Repository;

import com.example.mott.Model.Organization;
import com.example.mott.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByOrganizationOrgId(UUID orgId);
}
