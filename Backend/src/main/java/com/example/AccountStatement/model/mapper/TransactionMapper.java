package com.example.AccountStatement.model.mapper;

import com.example.AccountStatement.model.dto.TransactionDTO;
import com.example.AccountStatement.model.entity.Transaction;

public class TransactionMapper {
    public static TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setReferenceNo(transaction.getReferenceNo());
        dto.setDate(transaction.getDate());
        dto.setAmount(transaction.getAmount());
        dto.setBalance(transaction.getBalance());
        dto.setJournalNo(transaction.getJournalNo());
        dto.setChequeNo(transaction.getChequeNo());
        dto.setStatementNarrative(transaction.getStatementNarrative());
        dto.setTransferNarrative(transaction.getTransferNarrative());
        return dto;
    }
}

