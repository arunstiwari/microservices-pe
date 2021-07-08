package com.tekmentor.customersvc.service;

import com.tekmentor.customersvc.exception.CustomerNotFoundException;
import com.tekmentor.customersvc.model.Customer;
import com.tekmentor.customersvc.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> fetchAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer fetchCustomerById(long id) {
        Customer customer = customerRepository.findCustomerById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " does not exist"));
        return customer;
    }
}
