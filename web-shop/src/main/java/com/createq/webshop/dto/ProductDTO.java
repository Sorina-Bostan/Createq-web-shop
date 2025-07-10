package com.createq.webshop.dto;

import com.createq.webshop.model.CategoryModel;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private int stockQuantity;
    private String categoryName;

    public String getCategoryName() {return categoryName;}
    public void setCategoryName(String categoryName) {this.categoryName = categoryName;}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
