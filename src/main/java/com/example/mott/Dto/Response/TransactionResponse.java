package com.example.mott.Dto.Response;

import com.example.mott.Enum.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {

    private UUID id;
    private TransactionType type;
    private double amount;
    private String itemDescription;
    private LocalDate date;
    private String party;
    private Instant createdAt;
    private UUID organizationId;
    private UUID createdById;
}