package com.example.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.entity.ProductCategory;
import com.example.product.entity.ProductInfo;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
	List<ProductInfo> findByCategoryIn(List<ProductCategory> categories);
//	List<ProductInfo> findByCategoryInAndGender(List<ProductCategory> categories, String gender);
//    List<ProductInfo> findByCategoryInAndCategory_Gender(List<ProductCategory> categories, String gender);
}