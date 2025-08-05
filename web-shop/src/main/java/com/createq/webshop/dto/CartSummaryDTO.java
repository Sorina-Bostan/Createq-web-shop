package com.createq.webshop.dto;

public class CartSummaryDTO {
    private int itemCount;

    public CartSummaryDTO(int itemCount) {
        this.itemCount = itemCount;
    }
    public int getItemCount() {
        return itemCount;
    }
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
