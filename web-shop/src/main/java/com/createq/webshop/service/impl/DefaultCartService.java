package com.createq.webshop.service.impl;

import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.dto.CartItemDTO;
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

import java.util.List;
import java.util.Optional;

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

    /*@Override
    public CartModel getCartForUser(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Cart not found for user: " + user.getUsername()));
    }

    @Override
    public CartModel addItemToCart(CartModel cart, Long productId, int quantity) {
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        if (product.getStockQuantity() < quantity) {
            throw new IllegalStateException("Not enough items in stock for product: " + product.getName());
        }

        Optional<CartItemModel> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItemModel existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + quantity;

            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalStateException("Cannot add more items than available in stock.");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItemModel newItem = new CartItemModel();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }
        return cartRepository.save(cart);
    }

    @Override
    public CartModel updateItemQuantity(CartModel cart, Long itemId, int newQuantity) {
        CartItemModel itemToUpdate = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart with id: " + itemId));
        if (itemToUpdate.getProduct().getStockQuantity() < newQuantity) {
            throw new IllegalStateException("Not enough items in stock.");
        }
        if (newQuantity <= 0) {
            return removeItemFromCart(cart, itemId);
        }

        itemToUpdate.setQuantity(newQuantity);
        cartItemRepository.save(itemToUpdate);
        return cartRepository.save(cart);
    }

    @Override
    public CartModel removeItemFromCart(CartModel cart, Long itemId) {
        cart.getCartItems().removeIf(item -> item.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public CartModel clearCart(CartModel cart) {
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }

    @Override
    public void mergeItemsIntoCart(CartModel cart, List<AddItemToCartDTO> itemsToMerge) {
        if (itemsToMerge == null) return;

        for (AddItemToCartDTO itemDto : itemsToMerge) {
            addItemToCart(cart, itemDto.getProductId(), itemDto.getQuantity());
        }
    }*/
}