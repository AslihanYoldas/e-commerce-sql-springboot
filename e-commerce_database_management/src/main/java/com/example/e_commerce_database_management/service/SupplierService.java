package com.example.e_commerce_database_management.service;

import com.example.e_commerce_database_management.entity.Supplier;

public interface SupplierService {
    Supplier getSupplierForCategory(String categoryName);

}
