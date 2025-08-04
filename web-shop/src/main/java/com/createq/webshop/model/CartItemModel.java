package com.createq.webshop.model;

import jakarta.persistence.*;

@Entity
@Table(name="Cart_Item")
public class CartItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private CartModel cart;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String imageUrl;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public CartModel getCart() {return cart;}
    public void setCart(CartModel cart) {this.cart = cart;}
    public ProductModel getProduct() {return product;}
    public void setProduct(ProductModel product) {this.product = product;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

}
