package com.example.AccountStatement.model.dto;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountStatement {
    private String customerName;
    private String accountNumber;
    private Date fromDate;
    private Date toDate;
    private List<TransactionDTO> transactions;

    // Getters and Setters
    
}
