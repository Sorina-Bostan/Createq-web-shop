package com.createq.webshop.controllers;
import com.createq.webshop.dto.*;
import com.createq.webshop.facades.CartFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/cart")
public class CartController {
    private final CartFacade cartFacade;

    @Autowired
    public CartController(CartFacade cartFacade) {
        this.cartFacade = cartFacade;
    }
    @GetMapping("/view")
    public String showCartPage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        if (currentUser != null) {
            CartDTO cartDto = cartFacade.getCartForCurrentUser(currentUser);
            model.addAttribute("cart", cartDto);
        }
        return "fragments/cartFragment";
    }

    @PostMapping("/merge")
    @ResponseBody
    public ResponseEntity<Void> mergeCart(@AuthenticationPrincipal UserDetails currentUser, @RequestBody List<CartItemDTO> localCartItems) {
        if (currentUser != null && localCartItems != null && !localCartItems.isEmpty()) {
            cartFacade.mergeLocalCartWithUserCart(currentUser, localCartItems);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary")
    @ResponseBody
    public ResponseEntity<CartSummaryDTO> getCartSummary(@AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) {
            return ResponseEntity.ok(new CartSummaryDTO(0));
        }
        CartSummaryDTO summary = cartFacade.getCartSummaryForCurrentUser(currentUser);
        return ResponseEntity.ok(summary);
    }
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<CartDTO> addItemToCart(@AuthenticationPrincipal UserDetails currentUser, @RequestBody CartItemDTO itemDto) {
        CartDTO updatedCart = cartFacade.addItemToCart(currentUser, itemDto.getProductId(), itemDto.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/update/{productId}")
    @ResponseBody
    public ResponseEntity<Void> updateItem(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long productId,  @RequestBody CartItemDTO itemDto) {
        cartFacade.updateCartItemQuantityByProductId(currentUser, productId, itemDto.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    @ResponseBody
    public ResponseEntity<Void> removeItemFromCart(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long productId) {
        cartFacade.removeItemFromCartByProductId(currentUser, productId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/clear")
    @ResponseBody
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            cartFacade.clearCart(currentUser);
        }
        return ResponseEntity.noContent().build();
    }
}

