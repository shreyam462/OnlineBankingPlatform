package com.example.AccountStatement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AccountStatement.model.dto.CustomerDTO;
import com.example.AccountStatement.model.entity.Customer;
import com.example.AccountStatement.service.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/details")
    public CustomerDTO getCurrentCustomerDetails() {
        // Retrieve the username from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Optional<Customer> customerOptional = customerService.findByUsername(username);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return customerService.getCustomerDetails(customer.getId());
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }
}
