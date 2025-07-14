package com.createq.webshop.controllers;

import com.createq.webshop.exception.ResourceNotFoundException;
import com.createq.webshop.facades.CategoryFacade;
import com.createq.webshop.facades.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/category/{categoryId}")
    public String getProducts(@PathVariable Long categoryId, Model model) {
        model.addAttribute("products", productFacade.getProductsByCategoryId(categoryId));
        return "fragments/productListFragment";
    }

    @GetMapping("/{productId}")
    public String getProductDetails(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productFacade.getProductById(productId));
        return "fragments/productDetailsFragment";
    }

    @GetMapping("")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productFacade.getAll());
        return "fragments/productListFragment";
    }

    // this method will be called when a method in this class throws a ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleProductNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}