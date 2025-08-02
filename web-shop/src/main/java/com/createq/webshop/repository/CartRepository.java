package com.createq.webshop.repository;

import com.createq.webshop.model.CartModel;
import com.createq.webshop.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartModel,Long> {
    //Optional<CartModel> findByUserWithItems(@Param("user") UserModel user);
    //Optional<CartModel> findByUser(UserModel user);
}
