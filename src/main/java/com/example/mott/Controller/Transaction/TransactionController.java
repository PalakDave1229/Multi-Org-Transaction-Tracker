package com.example.mott.Controller.Transaction;

import com.example.mott.Dto.Request.TransactionRequest;
import com.example.mott.Dto.Response.TransactionResponse;
import com.example.mott.Service.Transaction.TransactionService;
import com.example.mott.Utility.ListResponseStructure;
import com.example.mott.Utility.ResponseBuilder;
import com.example.mott.Utility.ResponseStructure;
import com.example.mott.Utility.SimpleErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
@Tag(name = "Transaction Controller", description = "Collection of API Endpoints for Managing Transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @Operation(description = "API Endpoint to Create a New Transaction",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Transaction Created Successfully")
            })
    public ResponseEntity<ResponseStructure<TransactionResponse>> createTransaction(
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseBuilder.success(HttpStatus.CREATED, "Transaction Created", response);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    @Operation(description = "API Endpoint to Update Existing Transaction",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transaction Updated Successfully"),
                    @ApiResponse(responseCode = "404", description = "Transaction Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<TransactionResponse>> updateTransaction(
            @Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.updateTransaction(request);
        return ResponseBuilder.success(HttpStatus.OK, "Transaction Updated Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    @Operation(description = "API Endpoint to Delete a Transaction by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transaction Deleted Successfully"),
                    @ApiResponse(responseCode = "404", description = "Transaction Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<Object>> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransactionById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Transaction Deleted Successfully", null);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(description = "API Endpoint to Retrieve Transaction by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transaction Retrieved Successfully"),
                    @ApiResponse(responseCode = "404", description = "Transaction Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ResponseStructure<TransactionResponse>> getTransactionById(@PathVariable UUID id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseBuilder.success(HttpStatus.OK, "Transaction Retrieved Successfully", response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all/{orgId}")
    @Operation(description = "API Endpoint to Retrieve All Transactions for an Organization",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Transactions Retrieved Successfully"),
                    @ApiResponse(responseCode = "404", description = "Organization Not Found", content = {
                            @Content(schema = @Schema(implementation = SimpleErrorResponse.class))
                    })
            })
    public ResponseEntity<ListResponseStructure<TransactionResponse>> getAllTransactions(@PathVariable UUID orgId) {
        List<TransactionResponse> response = transactionService.getAllTransactionsForOrganization(orgId);
        return ResponseBuilder.success(HttpStatus.OK, "Transactions fetched successfully", response);
    }
}