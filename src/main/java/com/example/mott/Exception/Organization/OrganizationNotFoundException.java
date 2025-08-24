package com.example.mott.Exception.Organization;

import lombok.Getter;

@Getter
public class OrganizationNotFoundException extends RuntimeException {
  public OrganizationNotFoundException(String message) {
    super(message);
  }
}
