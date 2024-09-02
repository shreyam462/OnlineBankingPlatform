package com.example.AccountStatement.service;

import com.example.AccountStatement.model.dto.TransactionDTO;
import com.example.AccountStatement.model.entity.Account;
import com.example.AccountStatement.model.entity.Transaction;
import com.example.AccountStatement.model.mapper.TransactionMapper;
import com.example.AccountStatement.repository.AccountRepository;
import com.example.AccountStatement.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<TransactionDTO> getTransactionsByAccountNumberAndDateRange(String accountNumber, Date fromDate, Date toDate) {
        List<Transaction> transactions = transactionRepository.findByAccountNumberAndDateRange(accountNumber, fromDate, toDate);
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO createTransaction(String accountNumber, TransactionDTO transactionDTO) {
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
        if (!accountOpt.isPresent()) {
            throw new RuntimeException("Account not found with account number: " + accountNumber);
        }

        Account account = accountOpt.get();
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setReferenceNo(transactionDTO.getReferenceNo());
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setBalance(transactionDTO.getBalance());
        transaction.setJournalNo(transactionDTO.getJournalNo());
        transaction.setChequeNo(transactionDTO.getChequeNo());
        transaction.setStatementNarrative(transactionDTO.getStatementNarrative());
        transaction.setTransferNarrative(transactionDTO.getTransferNarrative());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(savedTransaction);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setReferenceNo(transactionDTO.getReferenceNo());
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setBalance(transactionDTO.getBalance());
        transaction.setJournalNo(transactionDTO.getJournalNo());
        transaction.setChequeNo(transactionDTO.getChequeNo());
        transaction.setStatementNarrative(transactionDTO.getStatementNarrative());
        transaction.setTransferNarrative(transactionDTO.getTransferNarrative());
        transaction = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
