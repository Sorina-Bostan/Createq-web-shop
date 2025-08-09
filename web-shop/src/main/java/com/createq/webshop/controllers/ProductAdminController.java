package com.createq.webshop.controllers;

import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.facades.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/products")
public class ProductAdminController {
    private final ProductFacade productFacade;
    @Autowired
    public ProductAdminController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProductsForAdmin() {
        List<ProductDTO> products = productFacade.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productFacade.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productFacade.updateProduct(productId, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productFacade.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


}
