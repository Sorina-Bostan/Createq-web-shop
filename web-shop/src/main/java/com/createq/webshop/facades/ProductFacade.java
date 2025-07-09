package com.createq.webshop.facades;

import com.createq.webshop.dto.ProductDTO;

import java.util.List;

public interface ProductFacade {
    public List<ProductDTO> getAll();
    List<ProductDTO> getProductsByCategoryId(Long categoryId);
    ProductDTO getProductById(Long productId);
}
