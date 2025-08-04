package com.createq.webshop.service.impl;

import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.model.CartItemModel;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.ProductModel;
import com.createq.webshop.model.UserModel;
import com.createq.webshop.repository.CartItemRepository;
import com.createq.webshop.repository.CartRepository;
import com.createq.webshop.repository.ProductRepository;
import com.createq.webshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import com.createq.webshop.exception.InsufficientStockException;

@Service
public class DefaultCartService implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DefaultCartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartModel getCartForUser(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user: " + user.getUsername()));
    }
    @Override
    @Transactional
    public CartModel addItemToCart(CartModel cart, Long productId, int quantity) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        Optional<CartItemModel> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        int currentQuantityInCart = existingItemOpt.map(CartItemModel::getQuantity).orElse(0);
        int requestedTotalQuantity = currentQuantityInCart + quantity;
        if (requestedTotalQuantity > product.getStockQuantity()) {
            int remainingStock = product.getStockQuantity() - currentQuantityInCart;
            String message = "Not enough stock for product: " + product.getName();
            throw new InsufficientStockException(message, remainingStock);
        }
        if (existingItemOpt.isPresent()) {
            CartItemModel existingItem = existingItemOpt.get();
            existingItem.setQuantity(requestedTotalQuantity);
        } else {
            CartItemModel newItem = new CartItemModel();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setName(product.getName());
            newItem.setPrice(product.getPrice());
            newItem.setImageUrl(product.getImageUrl());

            cart.getCartItems().add(newItem);
        }
        return cartRepository.save(cart);
    }
    @Override
    @Transactional
    public void mergeItemsIntoCart(CartModel cart, List<AddItemToCartDTO> itemsToMerge) {
        if (itemsToMerge == null) return;

        for (AddItemToCartDTO itemDto : itemsToMerge) {
            addItemToCart(cart, itemDto.getProductId(), itemDto.getQuantity());
        }
    }
    @Override
    @Transactional
    public CartModel updateItemQuantityByProductId(CartModel cart, Long productId, int newQuantity) {
        if (newQuantity <= 0) {
            return removeItemByProductId(cart, productId);
        }
        CartItemModel itemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
        if (newQuantity > itemToUpdate.getProduct().getStockQuantity()) {
            throw new IllegalStateException("Not enough stock available.");
        }

        itemToUpdate.setQuantity(newQuantity);
        cartItemRepository.save(itemToUpdate);
        return cart;
    }
    @Override
    @Transactional
    public CartModel removeItemByProductId(CartModel cart, Long productId) {
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }
    @Override
    @Transactional
    public CartModel clearCart(CartModel cart) {
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}