package com.tekmentor.productservice.service;

import com.tekmentor.productservice.exception.ProductNotFoundException;
import com.tekmentor.productservice.model.Product;
import com.tekmentor.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> fetchAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product addProduct(Product product) {
        log.info("addProduct: {} ",product);
        return productRepository.save(product);
    }

    public Product fetchSpecificProduct(long id) {

        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent())
            return byId.get();
        throw new ProductNotFoundException("Product with id "+id+ " does not exist");
    }
}