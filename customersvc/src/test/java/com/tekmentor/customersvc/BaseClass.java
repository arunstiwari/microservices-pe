package com.tekmentor.customersvc;

import com.tekmentor.customersvc.controller.CustomerController;
import com.tekmentor.customersvc.model.Customer;
import com.tekmentor.customersvc.service.CustomerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BaseClass {
    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerService customerService;

    @BeforeEach
    public void setup(){
        RestAssuredMockMvc.standaloneSetup(customerController);
        when(customerService.fetchCustomerById(1l))
                            .thenReturn(new Customer(1l, "Customer-1"));
       when(customerService.fetchAllCustomers())
                            .thenReturn(List.of(
                                    new Customer(1l, "Customer-1")
                            ));

    }
}
