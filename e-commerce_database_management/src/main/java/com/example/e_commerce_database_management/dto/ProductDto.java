package com.example.e_commerce_database_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductDto {
private String title;	
private float price;
private String description;
private String category;
private String image;

}
