package com.example.ecommerce.service;

import com.example.ecommerce.dto.CustomerDTO;
import com.example.ecommerce.dto.converter.CustomerDTOConverter;
import com.example.ecommerce.dto.request.CreateCustomerRequest;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOConverter customerDTOConverter;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, CustomerDTOConverter customerDTOConverter) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOConverter = customerDTOConverter;
    }

    public CustomerDTO createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = Customer.builder()
                .name(createCustomerRequest.getName())
                .password(passwordEncoder.encode(createCustomerRequest.getPassword()))
                .email(createCustomerRequest.getEmail())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return customerDTOConverter.convertToDTO(savedCustomer);
    }
}
