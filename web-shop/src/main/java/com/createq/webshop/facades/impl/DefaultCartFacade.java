package com.createq.webshop.facades.impl;

import com.createq.webshop.converter.CartConverter;
import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.facades.CartFacade;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;
import com.createq.webshop.service.CartService;
import com.createq.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultCartFacade implements CartFacade {
    private final CartService cartService;
    private final UserService userService;
    private final CartConverter cartConverter;
    @Autowired
    public DefaultCartFacade(CartService cartService, UserService userService, CartConverter cartConverter) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartConverter = cartConverter;
    }
    /*@Override
    public CartDTO getCartForCurrentUser(UserDetails currentUser) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel cart = cartService.getCartForUser(user);
        return cartConverter.convert(cart);
    }
    @Override
    public CartDTO addItemToCart(UserDetails currentUser, Long productId, int quantity) {

        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        CartModel updatedCart = cartService.addItemToCart(userCart, productId, quantity);
        return cartConverter.convert(updatedCart);
    }

    @Override
    public CartDTO updateCartItemQuantity(UserDetails currentUser, Long itemId, int newQuantity) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        CartModel updatedCart = cartService.updateItemQuantity(userCart, itemId, newQuantity);
        return cartConverter.convert(updatedCart);
    }

    @Override
    public CartDTO removeItemFromCart(UserDetails currentUser, Long itemId) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        CartModel updatedCart = cartService.removeItemFromCart(userCart, itemId);
        return cartConverter.convert(updatedCart);
    }

    @Override
    public CartDTO clearCart(UserDetails currentUser) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        CartModel updatedCart = cartService.clearCart(userCart);
        return cartConverter.convert(updatedCart);
    }

    private UserModel findUserFromDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("User details cannot be null.");
        }
        String username = userDetails.getUsername();
        return userService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found..."));
    }
    @Override
    public void mergeLocalCartWithUserCart(UserDetails currentUser, List<AddItemToCartDTO> localCartItems) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        cartService.mergeItemsIntoCart(userCart, localCartItems);
    }*/
}
