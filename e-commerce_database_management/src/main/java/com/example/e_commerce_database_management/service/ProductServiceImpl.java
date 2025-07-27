package com.example.e_commerce_database_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.apiClient.ProductApiClient;
import com.example.e_commerce_database_management.dto.ProductDto;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.entity.Supplier;
import com.example.e_commerce_database_management.mapper.ProductMapper;
import com.example.e_commerce_database_management.repository.CategoryRepository;
import com.example.e_commerce_database_management.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductApiClient productApiClient;
    private final CategoryRepository categoryRepository;
    private final SupplierService supplierService;

    @Override
    public List<Product> importProducts() {

        List<ProductDto> productsDtos = productApiClient.getProducts();

        // Mapping productdtos to products
        List<Product> products = productsDtos.stream().map(dto -> {
            // get category from database
            Category category = categoryRepository
                    .findByCategoryName(WordUtils.capitalizeFully(dto.getCategory()))
                    // if category does not exist in db use 'Not Known' category and if that not
                    // exist either save that to db
                    .orElseGet(() -> categoryRepository.findByCategoryName("Not Known")
                            .orElseGet(() -> categoryRepository.save(new Category(null, "Not Known", null))));

            // If there is a parent category get that else category itself
            Category categoryDB;
            if (category.getCategoryParentId() != null) {
                categoryDB = categoryRepository.findById(category.getCategoryParentId()).orElseGet(() -> category);
            } else {
                categoryDB = category;
            }

            Supplier supplier = supplierService.getSupplierForCategory(categoryDB.getCategoryName());

            return ProductMapper.mapToProduct(dto, category, supplier);

        }).collect(Collectors.toList());
        productRepository.saveAll(products);
        return products;
    }

}
