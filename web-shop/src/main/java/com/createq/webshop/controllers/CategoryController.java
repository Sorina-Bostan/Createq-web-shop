package com.createq.webshop.controllers;

import com.createq.webshop.dto.CategoryDTO;
import com.createq.webshop.facades.CategoryFacade;
import com.createq.webshop.facades.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    private final ProductFacade productFacade;
    private final CategoryFacade categoryFacade;

    @Autowired
    public CategoryController(ProductFacade productFacade, CategoryFacade categoryFacade) {
        this.productFacade = productFacade;
        this.categoryFacade = categoryFacade;
    }

    @GetMapping("categories")
    public String showMainPageWithCategories(Model model) {
        model.addAttribute("categories", categoryFacade.getAll());
        return "allCategories";
    }
}
