package com.createq.webshop.service.impl;

import com.createq.webshop.exception.ResourceNotFoundException;
import com.createq.webshop.model.ProductModel;
import com.createq.webshop.repository.ProductRepository;
import com.createq.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public ProductRepository getProductRepository() {
        return productRepository;
    }

    @Override
    public List<ProductModel> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public List<ProductModel> getProductsByCategoryId(Long categoryId) {
        return this.productRepository.findByCategoryId(categoryId);
    }
    @Override
    public ProductModel getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(productId + " not found."));
    }
}
