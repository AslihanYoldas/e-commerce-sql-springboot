package com.example.e_commerce_database_management.service;

import java.util.List;

import com.example.e_commerce_database_management.dto.ProductCrudDto;
import com.example.e_commerce_database_management.dto.ProductImportDto;
import com.example.e_commerce_database_management.entity.Product;


public interface ProductService {

    // Importing data from an external API
    List<Product> importProducts();

    // CRUD Operations
    Product create(ProductCrudDto dto);
    List<ProductCrudDto> getAllProducts();
    ProductImportDto updateProduct(Long id, ProductImportDto updatedDto);
    ProductImportDto deleteProduct(Long id);


    

}
