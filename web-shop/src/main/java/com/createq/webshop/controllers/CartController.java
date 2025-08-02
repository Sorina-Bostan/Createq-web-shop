package com.createq.webshop.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/cart")
public class CartController {
    @GetMapping("/view")
    public String showCartPage() {
        return "fragments/cartFragment";
    }
}

