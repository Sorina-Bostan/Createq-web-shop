package com.createq.webshop.service.impl;

import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.exception.ResourceNotFoundException;
import com.createq.webshop.model.CategoryModel;
import com.createq.webshop.model.ProductModel;
import com.createq.webshop.repository.CategoryRepository;
import com.createq.webshop.repository.ProductRepository;
import com.createq.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DefaultProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
    @Override
    @Transactional
    public ProductModel createProduct(ProductDTO dto) {
        CategoryModel category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID: " + dto.getCategoryId()));

        ProductModel product = new ProductModel();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductModel updateProduct(Long id, ProductDTO dto) {
        ProductModel existingProduct = getProductById(id);

        CategoryModel category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID: " + dto.getCategoryId()));

        existingProduct.setName(dto.getName());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setStockQuantity(dto.getStockQuantity());
        existingProduct.setImageUrl(dto.getImageUrl());
        existingProduct.setCategory(category);

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found, cannot delete.");
        }
        productRepository.deleteById(id);
    }
}
