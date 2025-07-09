package com.createq.webshop.repository;

import com.createq.webshop.model.ProductModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel,Long> {
    List<ProductModel> findByCategoryId(Long categoryId);
}
