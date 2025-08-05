package com.createq.webshop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
    @Column(name = "total_items", nullable = false)
    private int totalItems;
    @Column(name = "total_price", nullable = false)
    private double totalPrice;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemModel> cartItems = new ArrayList<>();

    public CartModel() {
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public UserModel getUser() {return user;}
    public void setUser(UserModel user) {this.user = user;}
    public List<CartItemModel> getCartItems() {return cartItems;}
    public void setCartItems(List<CartItemModel> cartItems) {this.cartItems = cartItems;}
    public int getTotalItems() {return totalItems;}
    public void setTotalItems(int totalItems) {this.totalItems = totalItems;}
    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
}
