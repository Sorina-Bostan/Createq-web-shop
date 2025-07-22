package com.createq.webshop.controllers;

import com.createq.webshop.dto.ProductDTO;
import com.createq.webshop.exception.ResourceNotFoundException;
import com.createq.webshop.facades.CategoryFacade;
import com.createq.webshop.facades.ProductFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }
    @GetMapping("/{productId}")
    public String getProductDetails(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productFacade.getProductById(productId));
        return "fragments/productDetailsFragment";
    }


    @GetMapping("")
    @ResponseBody
    public List<ProductDTO> getAllProducts() {
        return productFacade.getAll();
    }
    @GetMapping("/category/{categoryId}")
    @ResponseBody
    public List<ProductDTO> getProducts(@PathVariable Long categoryId) {
        return productFacade.getProductsByCategoryId(categoryId);
    }

    // this method will be called when a method in this class throws a ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleProductNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}