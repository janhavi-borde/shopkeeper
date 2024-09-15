package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.product.entity.ProductCategory;
import com.example.product.entity.ProductInfo;
import com.example.product.repository.ProductCategoryRepository;
import com.example.product.repository.ProductInfoRepository;

@Service
public class ProductInfoService {
    @Autowired
    private ProductInfoRepository repository;
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Page<ProductInfo> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProductInfo> getProductById(Long id) {
        return repository.findById(id);
    }

    public ProductInfo saveProduct(ProductInfo product) {
    	 ProductCategory category = productCategoryRepository.findById(product.getCategory().getId())
                 .orElseThrow(() -> new RuntimeException("Category not found"));
         product.setCategory(category);
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
    
    
    public List<ProductInfo> getProductsByCategory(String categoryName) {
        List<ProductCategory> categories = productCategoryRepository.findByName(categoryName);
        return repository.findByCategoryIn(categories);
    }
    
    public List<ProductInfo> getProductsByCategoryAndGender(Optional<String> categoryName, Optional<String> gender) {
        List<ProductCategory> categories;
        if (categoryName.isPresent() && gender.isPresent()) {
            categories = productCategoryRepository.findByNameAndGender(categoryName.get(), gender.get());
        }
        else if (categoryName.isPresent()) {
            categories = productCategoryRepository.findByName(categoryName.get());
        }
        else if (gender.isPresent()) {
            categories = productCategoryRepository.findByGender(gender.get());
        } else {
            return repository.findAll();
        }
        return repository.findByCategoryIn(categories);
    }
}