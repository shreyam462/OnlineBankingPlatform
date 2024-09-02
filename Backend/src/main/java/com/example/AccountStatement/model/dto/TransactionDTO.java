package com.example.AccountStatement.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class TransactionDTO {
    private Long id;
    private String referenceNo;
    private Date date;
    private BigDecimal amount;
    private BigDecimal balance;
    private String journalNo;
    private String chequeNo;
    private String statementNarrative;
    private String transferNarrative;
}

