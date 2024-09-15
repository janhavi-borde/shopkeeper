package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.entity.ProductCategory;
import com.example.product.entity.ProductInfo;
import com.example.product.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    public List<ProductCategory> getAllCategories() {
        return repository.findAll();
    }

    public Optional<ProductCategory> getCategoryById(Long id) {
        return repository.findById(id);
    }

    public ProductCategory saveCategory(ProductCategory category) {
        return repository.save(category);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }
}