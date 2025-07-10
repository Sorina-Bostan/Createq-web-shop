package com.createq.webshop.service.impl;

import com.createq.webshop.model.CategoryModel;
import com.createq.webshop.repository.CategoryRepository;
import com.createq.webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
    @Autowired
    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryModel> getAll() { return this.categoryRepository.findAll(); }
}
