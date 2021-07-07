package com.tekmentor.productservice.service;

import com.tekmentor.productservice.exception.CategoryNotFoundException;
import com.tekmentor.productservice.model.Category;
import com.tekmentor.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> showAllCategories() {
        return  categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        Category save = categoryRepository.save(category);
        return save;
    }

    public Category findCategoryById(long id) {
        Supplier<CategoryNotFoundException> exceptionSupplier = () -> new CategoryNotFoundException("Category with id "+id+ " is not found");
        return categoryRepository.findById(id).orElseThrow(exceptionSupplier);
    }
}