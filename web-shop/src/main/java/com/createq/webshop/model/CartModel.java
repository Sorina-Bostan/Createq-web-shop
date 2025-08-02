package com.createq.webshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "cart")
    private UserModel user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemModel> cartItems = new ArrayList<>();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public UserModel getUser() {return user;}
    public void setUser(UserModel user) {this.user = user;}
    public List<CartItemModel> getCartItems() {return cartItems;}
    public void setCartItems(List<CartItemModel> cartItems) {this.cartItems = cartItems;}
}
