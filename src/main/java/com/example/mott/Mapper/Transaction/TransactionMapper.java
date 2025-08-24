package com.example.mott.Mapper.Transaction;

import com.example.mott.Dto.Request.TransactionRequest;
import com.example.mott.Dto.Response.TransactionResponse;
import com.example.mott.Model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TransactionMapper {


    Transaction mapToTransaction(TransactionRequest transactionRequest);

    void mapToTransactionEntity(TransactionRequest transactionRequest, @MappingTarget Transaction transaction);


    TransactionResponse mapToTransactionResponse(Transaction transaction);

    List<TransactionResponse> mapToTransactionResponse(List<Transaction> transactionList);
}