package com.example.e_commerce_database_management.apiClient;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.e_commerce_database_management.constants.ConstantValues;

import com.example.e_commerce_database_management.dto.ProductDto;

@Component
public class ProductApiClient {

    private final RestTemplate restTemplate;

    public ProductApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

     public List<ProductDto> getProducts() {
        String url = ConstantValues.FAKE_STORE_API_URL +"/products";
        ProductDto[] productDtos = restTemplate.getForObject(url, ProductDto[].class);
        System.out.println(Arrays.asList(productDtos));
        return Arrays.asList(productDtos);
    }

    public List<String> getCategories(){
        String url = ConstantValues.FAKE_STORE_API_URL +"/products/categories";
        ResponseEntity<String[]> response = restTemplate.getForEntity(url,
         String[].class);
         return Arrays.asList(response.getBody());
 
    }
}
