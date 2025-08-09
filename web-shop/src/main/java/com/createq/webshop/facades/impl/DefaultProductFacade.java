package com.createq.webshop.facades.impl;


import com.createq.webshop.converter.ProductConverter;
import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.facades.ProductFacade;
import com.createq.webshop.model.ProductModel;
import com.createq.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultProductFacade implements ProductFacade {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Autowired
    public DefaultProductFacade(ProductService productService, ProductConverter productConverter) {
        this.productService = productService;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDTO> getAllProducts(){
        List<ProductModel> productModels = productService.getAll();
        List<ProductDTO> productDTOS = productConverter.convertAll(productModels);
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        List<ProductModel> models = productService.getProductsByCategoryId(categoryId);
        return productConverter.convertAll(models);
    }
    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        ProductModel productModel = productService.getProductById(productId);
        return productConverter.convert(productModel);
    }
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductModel createdModel = productService.createProduct(productDTO);
        return productConverter.convert(createdModel);
    }
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        ProductModel updatedModel = productService.updateProduct(id, productDTO);
        return productConverter.convert(updatedModel);
    }

    @Override
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }
}
