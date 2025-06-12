package com.example.e_commerce_database_management.mapper;


import com.example.e_commerce_database_management.dto.ProductDto;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.entity.Supplier;



public  class ProductMapper {
   
    public static ProductDto mapToProductDto(Product product){

        return new ProductDto(
            product.getName(),
            product.getPrice(),
            product.getDesc(),
            product.getCategory().getCategoryName(),
            product.getImage()
        );
    }

    public static Product mapToProduct(ProductDto productDto,Category cat, Supplier supplier){

        return new Product(
            null,
            supplier,
            cat!= null ? cat:new Category(null, "Not Known" ,null),
            productDto.getTitle(),
            productDto.getPrice(),
            productDto.getDescription(),
            productDto.getImage()
        );
    }

}
