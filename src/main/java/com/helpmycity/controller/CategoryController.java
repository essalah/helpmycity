package com.helpmycity.controller;

import com.helpmycity.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/api/categories")
    public Object getCategories() {

        return categoryRepository.findAll();

    }
}
