package com.example.AccountStatement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    @Column(name = "date", nullable = false)
    private java.sql.Date date;

    @Column(name = "amount", nullable = false)
    private java.math.BigDecimal amount;

    @Column(name = "balance", nullable = false)
    private java.math.BigDecimal balance;

    @Column(name = "journal_no")
    private String journalNo;

    @Column(name = "cheque_no")
    private String chequeNo;

    @Column(name = "statement_narrative")
    private String statementNarrative;

    @Column(name = "transfer_narrative")
    private String transferNarrative;

    // Getters and Setters
}
