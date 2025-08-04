package com.createq.webshop.service;

import com.createq.webshop.dto.AddItemToCartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;

import java.util.List;

public interface CartService {
    CartModel getCartForUser(UserModel user);
    CartModel addItemToCart(CartModel cart, Long productId, int quantity);
    void mergeItemsIntoCart(CartModel cart, List<AddItemToCartDTO> itemsToMerge);
    CartModel updateItemQuantityByProductId(CartModel cart, Long productId, int newQuantity);
    CartModel removeItemByProductId(CartModel cart, Long productId);
    CartModel clearCart(CartModel cart);
}