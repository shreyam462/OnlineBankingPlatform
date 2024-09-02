package com.example.AccountStatement.repository;

import com.example.AccountStatement.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber AND t.date BETWEEN :fromDate AND :toDate")
    List<Transaction> findByAccountNumberAndDateRange(
            @Param("accountNumber") String accountNumber,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
}
