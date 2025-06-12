package com.example.e_commerce_database_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.apiClient.ProductApiClient;
import com.example.e_commerce_database_management.constants.ConstantValues;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final ProductApiClient productApiClient;
    private final CategoryRepository categoryRepository;

    @Override
    public List<String> importCategories() {
        List<String> apiCategories = productApiClient.getCategories();
        Category parent=null;
        for (String catName : apiCategories) {
            // Get the parent name (for example for women clothing get fashion )
            String parentName = ConstantValues.PARENT_CATEGORY_MAP.get(catName);

            if (parentName != null) {
                // get the parent category from the database
                // if parent not exist on database insert to the database
                parent = categoryRepository.findByCategoryName(parentName)
                        .orElse(categoryRepository.save(new Category(null, parentName, null)));

            }
            // if category name from api does not exist insert to the table with its parent if it exists
            categoryRepository.findByCategoryName(catName)
                    .orElse(categoryRepository.save(new Category(null, catName, parent!= null?parent.getId():null)));

        }
        return apiCategories;

    };

}
