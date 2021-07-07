package com.tekmentors.customerservice.service;

import com.tekmentors.customerservice.exception.CustomerNotFoundException;
import com.tekmentors.customerservice.model.Customer;
import com.tekmentors.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> fetchAllCustomers() {
        return repository.findAll();
    }

    public Customer addNewCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer findCustomerById(long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer with id "+id+ " does not exist"));
    }
}
