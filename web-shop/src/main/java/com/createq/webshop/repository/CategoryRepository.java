package com.createq.webshop.repository;

import com.createq.webshop.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel,Long> {
}
