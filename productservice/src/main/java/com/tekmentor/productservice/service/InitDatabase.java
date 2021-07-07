package com.tekmentor.productservice.service;

import com.tekmentor.productservice.model.Category;
import com.tekmentor.productservice.model.Product;
import com.tekmentor.productservice.repository.CategoryRepository;
import com.tekmentor.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitDatabase {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void initializeDatabase(){
        List<Category> categories = List.of(
                new Category(null, "Books"),
                new Category(null, "Toys"),
                new Category(null, "Electronics")
        );
        List<Category> categories1 = categoryRepository.saveAll(categories);

        List<Product> products = List.of(
                new Product(null, "Children Toys", "Product-1", 12.0, categories1.get(1)),
                new Product(null, "Laptop for Students", "Product-2", 1200.0, categories1.get(2)),
                new Product(null, "Fictional books", "Product-3", 22.0, categories1.get(0))
        );
        productRepository.saveAll(products);
    }
}