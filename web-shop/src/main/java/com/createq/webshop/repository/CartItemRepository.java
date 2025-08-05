package com.createq.webshop.repository;

import com.createq.webshop.model.CartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemModel,Long> {
}
