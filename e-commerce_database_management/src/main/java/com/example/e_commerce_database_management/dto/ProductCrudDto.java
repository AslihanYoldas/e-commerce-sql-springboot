package com.example.e_commerce_database_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCrudDto extends ProductDto {

String supplierName;

public ProductCrudDto(String supplierName, String title, float price, String description, String category, String image) {
    super(title, price, description, category, image);
    this.supplierName = supplierName;

    
    }
}
