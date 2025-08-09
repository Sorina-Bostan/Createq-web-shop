package com.createq.webshop.controllers;


import com.createq.webshop.dto.CategoryDTO;
import com.createq.webshop.facades.CategoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {
    private final CategoryFacade categoryFacade;

    @Autowired
    public CategoryApiController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryFacade.getAll();
        return ResponseEntity.ok(categories);
    }

}
