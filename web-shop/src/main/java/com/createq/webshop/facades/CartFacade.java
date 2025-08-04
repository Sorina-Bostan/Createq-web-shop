package com.createq.webshop.facades;

import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.dto.CartSummaryDTO;
import com.createq.webshop.model.CartModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CartFacade {
    CartDTO getCartForCurrentUser(UserDetails currentUser);
    CartDTO addItemToCart(UserDetails currentUser, Long productId, int quantity);
    void mergeLocalCartWithUserCart(UserDetails currentUser, List<AddItemToCartDTO> localCartItems);
    CartSummaryDTO getCartSummaryForCurrentUser(UserDetails currentUser);
    void updateCartItemQuantityByProductId(UserDetails currentUser, Long productId, int newQuantity);
    void removeItemFromCartByProductId(UserDetails currentUser, Long productId);
    void clearCart(UserDetails currentUser);
}

