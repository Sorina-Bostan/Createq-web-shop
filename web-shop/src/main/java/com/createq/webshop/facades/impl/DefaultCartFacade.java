package com.createq.webshop.facades.impl;

import com.createq.webshop.converter.CartConverter;
import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.dto.CartSummaryDTO;
import com.createq.webshop.facades.CartFacade;
import com.createq.webshop.model.CartItemModel;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;
import com.createq.webshop.repository.CartRepository;
import com.createq.webshop.service.CartService;
import com.createq.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DefaultCartFacade implements CartFacade {
    private final CartService cartService;
    private final UserService userService;
    private final CartConverter cartConverter;
    private final CartRepository cartRepository;
    @Autowired
    public DefaultCartFacade(CartService cartService, UserService userService, CartConverter cartConverter,CartRepository cartRepository) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartConverter = cartConverter;
        this.cartRepository = cartRepository;
    }
    @Override
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
    }

    @Override
    public CartSummaryDTO getCartSummaryForCurrentUser(UserDetails currentUser) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel cart = cartService.getCartForUser(user);

        int totalItems = cart.getCartItems().stream()
                .mapToInt(CartItemModel::getQuantity)
                .sum();

        return new CartSummaryDTO(totalItems);
    }
    @Override
    public void updateCartItemQuantityByProductId(UserDetails currentUser, Long productId, int newQuantity) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        cartService.updateItemQuantityByProductId(userCart, productId, newQuantity);
    }
    @Override
    public void removeItemFromCartByProductId(UserDetails currentUser, Long productId) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        cartService.removeItemByProductId(userCart, productId);
    }
    @Override
    public void clearCart(UserDetails currentUser) {
        UserModel user = findUserFromDetails(currentUser);
        CartModel userCart = cartService.getCartForUser(user);
        cartService.clearCart(userCart);
    }
}
