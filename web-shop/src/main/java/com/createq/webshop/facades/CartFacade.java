package com.createq.webshop.facades;
import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.dto.CartSummaryDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CartFacade {
    CartDTO getCartForCurrentUser(UserDetails currentUser);
    CartDTO addItemToCart(UserDetails currentUser, Long productId, int quantity);
    void mergeLocalCartWithUserCart(UserDetails currentUser, List<CartItemDTO> localCartItems);
    CartSummaryDTO getCartSummaryForCurrentUser(UserDetails currentUser);
    void updateCartItemQuantityByProductId(UserDetails currentUser, Long productId, int newQuantity);
    void removeItemFromCartByProductId(UserDetails currentUser, Long productId);
    void clearCart(UserDetails currentUser);
}

