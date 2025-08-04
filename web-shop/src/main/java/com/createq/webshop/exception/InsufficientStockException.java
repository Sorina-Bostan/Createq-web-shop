package com.createq.webshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends RuntimeException {

    private final int remainingStock;

    public InsufficientStockException(String message, int remainingStock) {
        super(message);
        this.remainingStock = remainingStock;
    }

    public int getRemainingStock() {
        return remainingStock;
    }
}
