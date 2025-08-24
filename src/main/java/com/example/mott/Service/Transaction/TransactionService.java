package com.example.mott.Service.Transaction;

import com.example.mott.Dto.Request.TransactionRequest;
import com.example.mott.Dto.Response.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    TransactionResponse updateTransaction(TransactionRequest transactionRequest);

    List<TransactionResponse> getAllTransactionsForOrganization(UUID orgId);

    TransactionResponse getTransactionById(UUID id);

    void deleteTransactionById(UUID id);
}