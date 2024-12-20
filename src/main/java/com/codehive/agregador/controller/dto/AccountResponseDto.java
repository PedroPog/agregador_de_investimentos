package com.codehive.agregador.controller.dto;

public class AccountResponseDto{
    private String accountId;
    private String description;

    public AccountResponseDto(String accountId, String description) {
        this.accountId = accountId;
        this.description = description;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
