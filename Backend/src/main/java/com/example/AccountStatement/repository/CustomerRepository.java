package com.example.AccountStatement.repository;

import com.example.AccountStatement.model.entity.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Customer findById(long id);
}

