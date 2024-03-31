package com.example.ecommerce.service;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //Yeni müşteri oluşturma
    public Customer addCustomer(Customer customer) {
        //TODO Spring Security password encode
        //customer.setPassword(customer.getPassword());
        return customerRepository.save(customer);
    }
}
