package com.createq.webshop.dto;

import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.ProductModel;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class CartItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private String name;
    private double price;
    private String imageUrl;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getProductId() {return productId;}
    public void setProductId(Long productId) {this.productId = productId;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public String getImageUrl() {return imageUrl;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
}
