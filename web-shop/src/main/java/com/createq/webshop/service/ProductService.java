package com.createq.webshop.service;

import com.createq.webshop.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> getAll();
    List<ProductModel> getProductsByCategoryId(Long categoryId);
    ProductModel getProductById(Long productId);
}
