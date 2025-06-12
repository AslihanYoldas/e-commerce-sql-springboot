package com.example.e_commerce_database_management.service;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Product> products = productsDtos.stream().map(dto -> {
            Category category = categoryRepository
                    .findByCategoryName(dto.getCategory())
                    .orElse(new Category(null, "Not Known", null));

            Supplier supplier = supplierService.getSupplierForCategory(category.getCategoryName());

            return ProductMapper.mapToProduct(dto, category,supplier);
        })

                .collect(Collectors.toList());
        productRepository.saveAll(products);
        return products;

    }

    

}
