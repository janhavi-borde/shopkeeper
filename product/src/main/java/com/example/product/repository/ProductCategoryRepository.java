package com.example.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	List<ProductCategory> findByName(String name);

    // Find categories by gender
    List<ProductCategory> findByGender(String gender);

    // Find categories by both name and gender
    List<ProductCategory> findByNameAndGender(String name, String gender);
}