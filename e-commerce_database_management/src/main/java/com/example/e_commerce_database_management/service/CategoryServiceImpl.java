package com.example.e_commerce_database_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.apiClient.ProductApiClient;
import com.example.e_commerce_database_management.constants.ConstantValues;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.repository.CategoryRepository;
import org.apache.commons.text.WordUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final ProductApiClient productApiClient;
    private final CategoryRepository categoryRepository;

    @Override
    public List<String> importCategories() {
        List<String> apiCategories = productApiClient.getCategories();
        for (String catName : apiCategories) {
            // Get the parent name (for example for women clothing get fashion )
            String parentName = ConstantValues.PARENT_CATEGORY_MAP.get(catName);
            // Java requires any variables accessed inside a lambda to be final or effectively final.
            // For that reason I use array
            Category[] parent = new Category[1];

            if (parentName != "") {
                
                // get the parent category from the database
                // if parent not exist on database insert to the database
                parent[0] = categoryRepository.findByCategoryName(WordUtils.capitalizeFully(parentName))
                        .orElseGet(()->categoryRepository.save(new Category(null, parentName, null)));

            }
            // if category name from api does not exist insert to the table with its parent if it exists
            categoryRepository.findByCategoryName(WordUtils.capitalizeFully(catName))
                    .orElseGet(()->categoryRepository.save(
                        new Category(null, WordUtils.capitalizeFully(catName), parent!= null?parent[0].getId():null)));

        }
        return apiCategories;

    };

}
