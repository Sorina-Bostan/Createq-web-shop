package com.createq.webshop.service;

import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.model.ProductModel;

import java.util.List;

public interface ProductService {
    List<ProductModel> getAll();
    List<ProductModel> getProductsByCategoryId(Long categoryId);
    ProductModel getProductById(Long productId);
    ProductModel createProduct(ProductDTO productDTO);
    ProductModel updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
