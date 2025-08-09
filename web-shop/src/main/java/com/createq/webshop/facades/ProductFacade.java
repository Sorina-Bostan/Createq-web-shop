package com.createq.webshop.facades;

import com.createq.webshop.dto.ProductDTO;

import java.util.List;

public interface ProductFacade {
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByCategoryId(Long categoryId);
    ProductDTO getProductById(Long productId);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
