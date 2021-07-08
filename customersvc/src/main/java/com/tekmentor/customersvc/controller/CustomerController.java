package com.tekmentor.customersvc.controller;

import com.tekmentor.customersvc.model.Customer;
import com.tekmentor.customersvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity fetchAllCustomers(){
        List<Customer> customers = customerService.fetchAllCustomers();
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity fetchCustomerById(@PathVariable("id") long id){
        Customer customer = customerService.fetchCustomerById(id);
        return new ResponseEntity(customer, HttpStatus.OK);
    }
}
