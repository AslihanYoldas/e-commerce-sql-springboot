package com.example.e_commerce_database_management.mapper;

import com.example.e_commerce_database_management.dto.ProductCrudDto;
import com.example.e_commerce_database_management.dto.ProductDto;
import com.example.e_commerce_database_management.dto.ProductImportDto;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.entity.Supplier;

public class ProductMapper {

    public static Product mapToProduct(ProductDto productDto, Category cat, Supplier supplier) {

        try {
            return new Product(
                    null,
                    supplier,
                    cat != null ? cat : new Category(null, "Not Known", null),
                    productDto.getTitle(),
                    productDto.getPrice(),
                    productDto.getDescription(),
                    productDto.getImage());
        } catch (Exception e) {
            System.out.println("ERROR ------------------  " + e.getMessage());
            return null;
        }
    }

    public static ProductImportDto mapToProductImportDto(Product product) {

        return new ProductImportDto(
                product.getName(),
                product.getPrice(),
                product.getDesc(),
                product.getCategory().getCategoryName(),
                product.getImage());
    }

    public static ProductCrudDto mapToProductCrudDto(Product product) {

        return new ProductCrudDto(
                product.getSupplier().getSupplierName(),
                product.getName(),
                product.getPrice(),
                product.getDesc(),
                product.getCategory().getCategoryName(),
                product.getImage()

        );
    }

}
