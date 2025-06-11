package com.example.e_commerce_database_management.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.apiClient.ProductApiClient;
import com.example.e_commerce_database_management.dto.ProductDto;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.mapper.ProductMapper;
import com.example.e_commerce_database_management.repository.CategoryRepository;
import com.example.e_commerce_database_management.repository.ProductRepository;
import com.example.e_commerce_database_management.constants.ConstantValues;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductApiClient productApiClient;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> importProducts() {
        List<ProductDto> productsDtos = productApiClient.getProducts();

        List<Product> products = productsDtos.stream().map(dto -> {
            Category category = categoryRepository
                    .findByCategoryName(dto.getCategory())
                    .orElse(new Category(null, "Not Known", null));

            return ProductMapper.mapToProduct(dto, category);
        })

                .collect(Collectors.toList());
        productRepository.saveAll(products);
        return products;

    }

    @Override
    public List<String> importCategories() {
        List<String> apiCategories = productApiClient.getCategories();
        Category parent;
        for (String catName : apiCategories) {
            // Get the parent name (for example for women clothing get fashion )
            String parentName = ConstantValues.parentCategoryMap.get(catName);

            if (parentName != null) {
                // get the parent category from the database
                // if parent not exist on database insert to the database
                parent = categoryRepository.findByCategoryName(parentName)
                        .orElse(categoryRepository.save(new Category(null, parentName, null)));

            }
            // if category name from api does not exist insert to the table
            categoryRepository.findByCategoryName(catName)
                    .orElse(categoryRepository.save(new Category(null, catName, null)));

        }
        return apiCategories;

    };

}
