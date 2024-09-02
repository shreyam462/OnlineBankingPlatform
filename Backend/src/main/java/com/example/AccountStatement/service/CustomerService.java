package com.example.AccountStatement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.AccountStatement.model.dto.CustomerDTO;
import com.example.AccountStatement.model.entity.Customer;
import com.example.AccountStatement.model.mapper.CustomerMapper;
import com.example.AccountStatement.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public CustomerDTO getCustomerDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                                               .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toDTO(customer);
    }
}
