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
    private int totalItems;
    private double totalPrice;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public List<CartItemDTO> getCartItems() {return cartItems;}
    public void setCartItems(List<CartItemDTO> cartItems) {this.cartItems = cartItems;}
    public int getTotalItems() {return totalItems;}
    public void setTotalItems(int totalItems) {this.totalItems = totalItems;}
    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}

}
