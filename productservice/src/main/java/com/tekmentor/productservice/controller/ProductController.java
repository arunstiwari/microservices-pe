package com.tekmentor.productservice.controller;

import com.tekmentor.productservice.exception.ErrorResponse;
import com.tekmentor.productservice.exception.ProductNotFoundException;
import com.tekmentor.productservice.model.Product;
import com.tekmentor.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @ExceptionHandler(value = {ProductNotFoundException.class})
    protected ResponseEntity<Object> handleException(ProductNotFoundException ex, WebRequest request){
        log.error(" exception : {}",ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setDetail(ex.getMessage());
        errorResponse.setInstance("/products/id");
        errorResponse.setTitle("Product Search");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products")
    public List<Product> showAllProducts(){
        return productService.fetchAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity fetchSpecificProduct(@PathVariable("id") long id){
        Product product = productService.fetchSpecificProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity addNewProduct(@RequestBody Product product){
        productService.addProduct(product);
        return new ResponseEntity("New Product Saved Successfully", HttpStatus.CREATED);
    }
}