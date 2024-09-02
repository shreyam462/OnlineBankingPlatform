package com.example.AccountStatement.model.mapper;

import com.example.AccountStatement.model.dto.AccountDTO;
import com.example.AccountStatement.model.entity.Account;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        return dto;
    }
}
