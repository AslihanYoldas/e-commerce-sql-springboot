package com.example.e_commerce_database_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    
    @GetMapping
    ResponseEntity<List<Product>> saveProducts(){
        List<Product> products = productService.importProducts();
        return ResponseEntity.ok(products);
        
    }







}
