package com.example.AccountStatement.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private String username;
    private String name;
    private String email;
    private AccountDTO account;
}
