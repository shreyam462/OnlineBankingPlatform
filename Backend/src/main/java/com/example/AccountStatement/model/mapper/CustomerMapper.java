package com.example.AccountStatement.model.mapper;

import org.springframework.stereotype.Component;

import com.example.AccountStatement.model.dto.CustomerDTO;
import com.example.AccountStatement.model.entity.Customer;

@Component
public class CustomerMapper {
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAccount(AccountMapper.toDTO(customer.getAccount()));
        return customerDTO;
    }
}
