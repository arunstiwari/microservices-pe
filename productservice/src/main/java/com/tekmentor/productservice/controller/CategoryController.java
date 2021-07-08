package com.tekmentor.productservice.controller;

import com.tekmentor.productservice.model.Category;
import com.tekmentor.productservice.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    public ResponseEntity showCategories(){
        List<Category> categories = categoryService.showAllCategories();
        return new ResponseEntity(categories, HttpStatus.OK);
    }

    @RequestMapping("/categories/{id}")
    public ResponseEntity showCategories(@PathVariable("id") long id){
       Category category = categoryService.findCategoryById(id);
        return new ResponseEntity(category, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity addCategory(@RequestBody Category category){
        Category createdCategory = categoryService.addCategory(category);
        return new ResponseEntity(createdCategory, HttpStatus.CREATED);
    }
}
