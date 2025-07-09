package com.createq.webshop.converter;

import com.createq.webshop.dto.CategoryDTO;
import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.model.CategoryModel;
import com.createq.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

@Component
public class CategoryConverter {
    private final ProductConverter productConverter;

    @Autowired
    public CategoryConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    CategoryDTO convert(CategoryModel categoryModel){
        CategoryDTO categoryDTO = new  CategoryDTO();
        categoryDTO.setId(categoryModel.getId());
        categoryDTO.setName(categoryModel.getName());
        if (categoryModel.getProducts() != null && !categoryModel.getProducts().isEmpty()) {
            List<ProductDTO> productDTOs = productConverter.convertAll(categoryModel.getProducts());
            categoryDTO.setProducts(productDTOs);
        }
        return categoryDTO;
    }
    public List<CategoryDTO> convertAll(List<CategoryModel> categoryModels) {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModels) {
            categoryDTOs.add(convert(categoryModel));
        }
        return categoryDTOs;
    }
}
