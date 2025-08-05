package com.example.e_commerce_database_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;

import com.example.e_commerce_database_management.apiClient.ProductApiClient;
import com.example.e_commerce_database_management.dto.ProductCrudDto;
import com.example.e_commerce_database_management.dto.ProductImportDto;
import com.example.e_commerce_database_management.entity.Category;
import com.example.e_commerce_database_management.entity.Product;
import com.example.e_commerce_database_management.entity.Supplier;
import com.example.e_commerce_database_management.mapper.ProductMapper;
import com.example.e_commerce_database_management.repository.CategoryRepository;
import com.example.e_commerce_database_management.repository.ProductRepository;
import com.example.e_commerce_database_management.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductApiClient productApiClient;
    private final CategoryRepository categoryRepository;
    private final SupplierService supplierService;
    private final SupplierRepository supplierRepository;

    @Override
    public List<Product> importProducts() {

        List<ProductImportDto> productsDtos = productApiClient.getProducts();

        // Mapping productdtos to products
        List<Product> products = productsDtos.stream().map(dto -> {
            return buildProductFromDto(dto);

        }).collect(Collectors.toList());
        productRepository.saveAll(products);
        return products;
    }

    public Product buildProductFromDto(ProductImportDto dto) {
        // get category from database
        Category category = findCategory(dto.getCategory());

        // If there is a parent category get that else category itself
        Category categoryDB;
        if (category.getCategoryParentId() != null) {
            categoryDB = categoryRepository.findById(category.getCategoryParentId()).orElseGet(() -> category);
        } else {
            categoryDB = category;
        }

        Supplier supplier = supplierService.getSupplierForCategory(categoryDB.getCategoryName());

        return ProductMapper.mapToProduct(dto, category, supplier);
    }

    // returns category if category is already in db else returns not known category
    Category findCategory(String categoryName){
        Category category = categoryRepository
        .findByCategoryName(WordUtils.capitalizeFully(categoryName))
        // if category does not exist in db use 'Not Known' category and if that not
        // exist either save that to db
        .orElseGet(() -> categoryRepository.findByCategoryName("Not Known")
        .orElseGet(() -> categoryRepository.save(new Category(null, "Not Known", null))));
        return category;
    }

    Supplier findSupplier(String supplierName){
        Supplier supplier = supplierRepository.findBySupplierName(supplierName)
        .orElseGet(()-> supplierRepository.findBySupplierName("Not Known")
        .orElseGet(()-> supplierRepository.save(new Supplier(null, "Not Known"))));
         return supplier;
    }

    // CRUD OPERATIONS

    @Override
    public Product create(ProductCrudDto dto) {

        Supplier supplier = findSupplier(dto.getSupplierName());
        Category category = findCategory(dto.getCategory());
        Product product = ProductMapper.mapToProduct(dto, category, supplier);
        productRepository.save(product);
        return product;

    }

    @Override
    public List<ProductCrudDto> getAllProducts() {
       return productRepository
                .findAll()
                .stream()
                .map(ProductMapper::mapToProductCrudDto)
                .collect(Collectors.toList());

    }

    @Override
    public ProductImportDto updateProduct(Long id, ProductImportDto updatedDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public ProductImportDto deleteProduct(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

}
