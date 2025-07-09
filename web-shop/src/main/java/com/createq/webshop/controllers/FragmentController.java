package com.createq.webshop.controllers;

import com.createq.webshop.facades.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fragments")
public class FragmentController {

    private final ProductFacade productFacade;

    @Autowired
    public FragmentController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/categories/{categoryId}/products")
    public String getProductsFragment(
            @PathVariable Long categoryId,
            Model model) {
        model.addAttribute("products", productFacade.getProductsByCategoryId(categoryId));

        return "fragments/productListFragment";
    }
    @GetMapping("/products/{productId}")
    public String getProductDetailsFragment(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productFacade.getProductById(productId));
        return "fragments/productDetailsFragment";
    }
}