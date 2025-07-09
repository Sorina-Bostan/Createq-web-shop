package com.createq.webshop.facades.impl;

import com.createq.webshop.converter.CategoryConverter;
import com.createq.webshop.dto.CategoryDTO;
import com.createq.webshop.facades.CategoryFacade;
import com.createq.webshop.model.CategoryModel;
import com.createq.webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultCategoryFacade implements CategoryFacade {
    private CategoryService categoryService;
    private CategoryConverter categoryConverter;
    @Autowired
    public DefaultCategoryFacade(CategoryService categoryService, CategoryConverter categoryConverter) {
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryModel> categoryModels = categoryService.getAll();
        List<CategoryDTO> categoryDTOS = categoryConverter.convertAll(categoryModels);
        return categoryDTOS;
    }
}
