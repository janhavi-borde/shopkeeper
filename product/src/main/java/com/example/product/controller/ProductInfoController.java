package com.example.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.entity.ProductInfo;
import com.example.product.service.ProductInfoService;

@RestController
@RequestMapping("/products")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

//    // Get all products
//    @GetMapping
//    public List<ProductInfo> getAllProducts() {
//        return productInfoService.getAllProducts();
//    }
    
    @GetMapping
    public Page<ProductInfo> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);  
        return productInfoService.getAllProducts(pageable);
    }
    	

    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductInfo> getProductById(@PathVariable Long id) {
        Optional<ProductInfo> product = productInfoService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductInfo> createProduct(@RequestBody ProductInfo product) {
        ProductInfo createdProduct = productInfoService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Update a product by ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductInfo> updateProduct(@PathVariable Long id, @RequestBody ProductInfo product) {
        if (productInfoService.getProductById(id).isPresent()) {
            product.setId(id);
            ProductInfo updatedProduct = productInfoService.saveProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productInfoService.getProductById(id).isPresent()) {
            productInfoService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/filter")
    public ResponseEntity<List<ProductInfo>> getProductsByFilter(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> gender) {

        List<ProductInfo> products = productInfoService.getProductsByCategoryAndGender(category, gender);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }
}