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

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public CartModel getCart() {return cart;}
    public void setCart(CartModel cart) {this.cart = cart;}
    public ProductModel getProduct() {return product;}
    public void setProduct(ProductModel product) {this.product = product;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

}
