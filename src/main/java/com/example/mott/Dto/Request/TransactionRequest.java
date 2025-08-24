package com.example.mott.Dto.Request;

import com.example.mott.Enum.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {

    private UUID id;
    private TransactionType type; // SALE or PURCHASE
    private double amount;
    private String itemDescription;
    private LocalDate date;
    private String party;
    private UUID organizationId;
}