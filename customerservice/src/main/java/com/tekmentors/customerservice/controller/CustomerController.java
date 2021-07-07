package com.tekmentors.customerservice.controller;

import com.tekmentors.customerservice.model.Customer;
import com.tekmentors.customerservice.repository.CustomerRepository;
import com.tekmentors.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity fetchAllCustomers(){
        List<Customer> customers = customerService.fetchAllCustomers();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @PostMapping("/customers")
    public ResponseEntity addNewCustomer(@RequestBody Customer customer){
        Customer createdCustomer = customerService.addNewCustomer(customer);
        return new ResponseEntity(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity fetchCustomerBySpecificId(@PathVariable("id") long id){
        Customer customerById = customerService.findCustomerById(id);
        return new ResponseEntity(customerById, HttpStatus.OK);
    }

}
