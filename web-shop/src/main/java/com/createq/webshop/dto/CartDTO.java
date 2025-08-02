package com.createq.webshop.dto;

import com.createq.webshop.model.CartItemModel;
import com.createq.webshop.model.UserModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public List<CartItemDTO> getCartItems() {return cartItems;}
    public void setCartItems(List<CartItemDTO> cartItems) {this.cartItems = cartItems;}
}
