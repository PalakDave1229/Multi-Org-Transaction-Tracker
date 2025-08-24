package com.example.mott.Exception.Transaction;

import lombok.Getter;

@Getter
public class TransactionNotFoundException extends RuntimeException {
  public TransactionNotFoundException(String message) {
    super(message);
  }
}
