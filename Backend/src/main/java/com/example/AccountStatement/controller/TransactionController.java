package com.example.AccountStatement.controller;

import com.example.AccountStatement.model.dto.TransactionDTO;
import com.example.AccountStatement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/list")
    public List<TransactionDTO> getTransactions(
            @RequestParam String accountNumber,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        try {
            Date from = new Date(dateFormat.parse(fromDate).getTime());
            Date to = new Date(dateFormat.parse(toDate).getTime());
            return transactionService.getTransactionsByAccountNumberAndDateRange(accountNumber, from, to);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format. Please use 'yyyy-MM-dd'.");
        }
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(
            @RequestParam String accountNumber,
            @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.createTransaction(accountNumber, transactionDTO);
        return ResponseEntity.ok(createdTransaction);
    }

     @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
