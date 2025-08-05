package com.createq.webshop.converter;

import com.createq.webshop.dto.CartDTO;
import com.createq.webshop.dto.CartItemDTO;
import com.createq.webshop.model.CartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CartConverter {
    private final CartItemConverter cartItemConverter;
    @Autowired
    public CartConverter(CartItemConverter cartItemConverter) {
        this.cartItemConverter = cartItemConverter;
    }
    public CartDTO convert(CartModel cartModel) {
        if (cartModel == null) {
            return null;
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cartModel.getId());
        cartDTO.setTotalItems(cartModel.getTotalItems());
        cartDTO.setTotalPrice(cartModel.getTotalPrice());
        if (cartModel.getCartItems() != null && !cartModel.getCartItems().isEmpty()) {
            List<CartItemDTO> cartItemDTOs = cartItemConverter.convertAll(cartModel.getCartItems());
            cartDTO.setCartItems(cartItemDTOs);
        } else {
            cartDTO.setCartItems(Collections.emptyList());
        }
        return cartDTO;
    }
}
