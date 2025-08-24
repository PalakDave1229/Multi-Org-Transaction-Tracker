package com.example.mott.Service.Transaction;

import com.example.mott.Dto.Request.TransactionRequest;
import com.example.mott.Dto.Response.TransactionResponse;
import com.example.mott.Exception.Transaction.TransactionNotFoundException;
import com.example.mott.Mapper.Transaction.TransactionMapper;
import com.example.mott.Model.Organization;
import com.example.mott.Model.Transaction;
import com.example.mott.Model.User;
import com.example.mott.Repository.OrganizationRepository;
import com.example.mott.Repository.TransactionRepository;
import com.example.mott.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Organization organization = organizationRepository.findById(transactionRequest.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        if (!organization.getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to organization");
        }

        Transaction transaction = transactionMapper.mapToTransaction(transactionRequest);
        transaction.setOrganization(organization);
        transaction.setCreatedBy(currentUser);
        transactionRepository.save(transaction);
        return transactionMapper.mapToTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse updateTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(transactionRequest.getId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with Id: " + transactionRequest.getId()));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!transaction.getOrganization().getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        transactionMapper.mapToTransactionEntity(transactionRequest, transaction);
        transactionRepository.save(transaction);
        return transactionMapper.mapToTransactionResponse(transaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactionsForOrganization(UUID orgId) {
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!organization.getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to organization");
        }
        List<Transaction> transactions = transactionRepository.findByOrganizationOrgId(orgId);
        return transactionMapper.mapToTransactionResponse(transactions);
    }

    @Override
    public TransactionResponse getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!transaction.getOrganization().getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        return transactionMapper.mapToTransactionResponse(transaction);
    }

    @Override
    public void deleteTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with Id: " + id));
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!transaction.getOrganization().getOwner().getEmail().equals(currentEmail)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        transactionRepository.delete(transaction);
    }
}