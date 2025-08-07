package com.createq.webshop.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    @GetMapping("/view-content")
    @PreAuthorize("hasRole('ADMIN')")
    public String getProductAdminPanel() {
        return "fragments/adminPanel";
    }
    @GetMapping("/add-user-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddUserFormFragment() {
        return "fragments/addUser";
    }
}
