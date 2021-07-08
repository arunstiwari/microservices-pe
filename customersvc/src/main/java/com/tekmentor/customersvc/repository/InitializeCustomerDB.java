package com.tekmentor.customersvc.repository;

import com.tekmentor.customersvc.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitializeCustomerDB {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init(){
        List<Customer> customers = List.of(
                new Customer(null, "customer-1"),
                new Customer(null, "customer-2")
        );
        customerRepository.saveAll(customers);
    }
}
